package com.github.nearapps.barcodescanner.presentation.views.fragments.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT_ERROR_CORRECTION_LEVEL
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT_FORMAT
import com.github.nearapps.barcodescanner.common.extensions.toIntent
import com.github.nearapps.barcodescanner.common.utils.BARCODE_KEY
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING
import com.github.nearapps.barcodescanner.databinding.FragmentMainScannerBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.domain.library.BeepManager
import com.github.nearapps.barcodescanner.domain.library.SettingsManager
import com.github.nearapps.barcodescanner.domain.library.VibratorAppCompat
import com.github.nearapps.barcodescanner.domain.library.camera.CameraZoomGestureDetector
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeAnalysisActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeScanFromImageGalleryActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.BaseActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.MainActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment
import com.github.nearapps.barcodescanner.presentation.views.utils.getMaxZoom
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.Result
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass.
 */
class MainScannerFragment : BaseFragment() {

    companion object {
        private const val ZXING_SCAN_INTENT_ACTION = "com.google.zxing.client.android.SCAN"
    }

    private var canEnableCamera = true

    private var codeScanner: CodeScanner? = null
    private val databaseBarcodeViewModel: DatabaseBarcodeViewModel by activityViewModel()

    // ---- View ----
    private var _binding: FragmentMainScannerBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureResultBarcodeScanFromImageActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        resultBarcodeScanFromImageActivity = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainScannerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()
        if(isCameraPermissionGranted()) doPermissionGranted() else askCameraPermission()
    }

    private fun configureMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_scanner, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId){
                R.id.menu_scanner_flash -> {
                    switchTorchFlash()
                    true
                }
                R.id.menu_scanner_scan_from_image -> {
                    startBarcodeScanFromImageActivity()
                    true
                }
                else -> false
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)

                if(hasFlash()) {
                    if (codeScanner?.isFlashEnabled == true)
                        menu.getItem(0).icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.baseline_flash_on_24)
                    else
                        menu.getItem(0).icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.baseline_flash_off_24)
                }else{
                    menu.getItem(0).isVisible = false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()

        if (isCameraPermissionGranted() && canEnableCamera) {
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        codeScanner?.let {
            it.isFlashEnabled = false
            it.releaseResources()
        }
        canEnableCamera=true
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = requireActivity()
        if(activity is BaseActivity){
            if(activity.settingsManager.isAutoScreenRotationScanDisabled) {
                activity.lockDeviceRotation(true)
            }
        }
    }

    // ---- Camera Permission ----

    private fun askCameraPermission() {
        // Gère le resultat de la demande de permission d'accès à la caméra.
        val requestPermission: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    doPermissionGranted()
                    codeScanner?.startPreview() // Uniquement lorsqu'on vient d'accepter la permission (dans les autres cas, startPreview() est appelé dans onResume()
                } else doPermissionRefused()
            }

        requestPermission.launch(Manifest.permission.CAMERA)
    }

    private fun isCameraPermissionGranted(): Boolean {
        val permission: Int = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun doPermissionGranted() {
        configureScanner()
        viewBinding.fragmentMainScannerInformationTextView.visibility = View.VISIBLE
        viewBinding.fragmentMainScannerCameraPermissionTextView.visibility = View.GONE
        viewBinding.fragmentMainScannerCodeScannerView.visibility = View.VISIBLE
    }

    private fun doPermissionRefused() {
        codeScanner?.releaseResources()
        codeScanner = null
        viewBinding.fragmentMainScannerInformationTextView.visibility = View.GONE
        viewBinding.fragmentMainScannerCameraPermissionTextView.visibility = View.VISIBLE
        viewBinding.fragmentMainScannerCodeScannerView.visibility = View.GONE
    }

    // ---- Flash ----

    private fun hasFlash(): Boolean =
        requireContext().applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    private fun switchTorchFlash(){
        codeScanner?.isFlashEnabled = codeScanner?.isFlashEnabled == false
        requireActivity().invalidateOptionsMenu()
    }

    // ---- Scan via Image ----

    private var resultBarcodeScanFromImageActivity: ActivityResultLauncher<Intent>? = null

    private fun configureResultBarcodeScanFromImageActivity(){
        resultBarcodeScanFromImageActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){

                it.data?.let { intentResult ->
                    canEnableCamera = false // C'est appelé avant onResume(), on empêche donc la réactivation de la caméra car elle n'est pas nécéssaire ici.
                    onSuccessfulScan(intentResult)
                }
            }
        }
    }

    private fun startBarcodeScanFromImageActivity(){
        if(codeScanner?.isFlashEnabled == true)
            switchTorchFlash()

        resultBarcodeScanFromImageActivity?.let { result ->
            val intent = createStartActivityIntent(requireContext(), BarcodeScanFromImageGalleryActivity::class)
            result.launch(intent)
        }
    }

    // ---- Scan ----

    private fun configureScanner(){

        codeScanner = CodeScanner(requireActivity(), viewBinding.fragmentMainScannerCodeScannerView).apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE

            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback(::onSuccessfulScan)
            errorCallback = ErrorCallback(::onErrorScan)

            configureZoom(this)
        }
    }

    private fun configureZoom(codeScanner: CodeScanner) {
        lifecycleScope.launchWhenResumed {
            val maxZoom: Int = codeScanner.getMaxZoom()
            if (maxZoom <= 0) return@launchWhenResumed
            codeScanner.zoom = get<SettingsManager>().getDefaultZoomValue()
            val defaultZoom: Float = codeScanner.zoom.toFloat()
            val slider = viewBinding.fragmentMainScannerCameraSlider.apply {
                valueTo = maxZoom.toFloat()
                value = defaultZoom
                visibility = View.VISIBLE
                addOnChangeListener { v, value, _ ->
                    codeScanner.zoom = value.roundToInt()
                    // BZZZTT!!1!
                    v.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                }
            }
            CameraZoomGestureDetector(defaultZoom / maxZoom)
                .attach(viewBinding.fragmentMainScannerCodeScannerView) {
                    slider.value = round(it * maxZoom) // step = 1f
                }
        }
    }

    private fun onSuccessfulScan(result: Result) = requireActivity().runOnUiThread {

        val contents = result.text
        val formatName = result.barcodeFormat?.name

        if(contents != null && formatName != null) {

            val settingsManager = get<SettingsManager>()

            if(settingsManager.shouldCopyBarcodeScan){
                copyToClipboard("contents", contents)
                showToastText(R.string.barcode_copied_label)
            }

            if(settingsManager.useBipScan)
                get<BeepManager>().playBeepSound(requireActivity())

            if(settingsManager.useVibrateScan)
                get<VibratorAppCompat>().vibrate()

            if(codeScanner?.isFlashEnabled == true)
                switchTorchFlash()

            val errorCorrectionLevel: QrCodeErrorCorrectionLevel =
                get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT)) { parametersOf(result) }

            val barcode: Barcode = get { parametersOf(contents, formatName, errorCorrectionLevel) }

            if(settingsManager.shouldAddBarcodeScanToHistory) {
                // Insert les informations du code-barres dans la base de données (de manière asynchrone)
                databaseBarcodeViewModel.insertBarcode(barcode)
            }

            // Si l'application a été ouverte via une application tierce
            if (requireActivity().intent?.action == ZXING_SCAN_INTENT_ACTION) {
                sendResultToAppIntent(result.toIntent())
            }else{
                startBarcodeAnalysisActivity(barcode)
            }
        } else {
            showSnackbar(getString(R.string.scan_cancel_label))
        }
    }

    private fun onSuccessfulScan(intentResult: Intent) = requireActivity().runOnUiThread {

        val contents = intentResult.getStringExtra(SCAN_RESULT)
        val formatName = intentResult.getStringExtra(SCAN_RESULT_FORMAT)

        if(contents != null && formatName != null) {

            val settingsManager = get<SettingsManager>()

            if(settingsManager.shouldCopyBarcodeScan){
                copyToClipboard("contents", contents)
                showToastText(R.string.barcode_copied_label)
            }

            if(settingsManager.useBipScan)
                get<BeepManager>().playBeepSound(requireActivity())

            if(settingsManager.useVibrateScan)
                get<VibratorAppCompat>().vibrate()

            if(codeScanner?.isFlashEnabled == true)
                switchTorchFlash()

            val errorCorrectionLevel: QrCodeErrorCorrectionLevel =
                get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING)) {
                    parametersOf(intentResult.getStringExtra(SCAN_RESULT_ERROR_CORRECTION_LEVEL))
                }

            val barcode: Barcode = get { parametersOf(contents, formatName, errorCorrectionLevel) }

            if(settingsManager.shouldAddBarcodeScanToHistory) {
                // Insert les informations du code-barres dans la base de données (de manière asynchrone)
                databaseBarcodeViewModel.insertBarcode(barcode)
            }

            // Si l'application a été ouverte via une application tierce
            if (requireActivity().intent?.action == ZXING_SCAN_INTENT_ACTION) {
                sendResultToAppIntent(intentResult)
            }else{
                startBarcodeAnalysisActivity(barcode)
            }
        } else {
            showSnackbar(getString(R.string.scan_cancel_label))
        }
    }

    private fun onErrorScan(t: Throwable) = requireActivity().runOnUiThread {
        showSnackbar(getString(R.string.scan_error_exception_label, t.message))
    }

    /**
     * Démarre l'Activity: BarcodeAnalysisActivity.
     */
    private fun startBarcodeAnalysisActivity(barcode: Barcode){
        val intent = createStartActivityIntent(requireContext(), BarcodeAnalysisActivity::class).apply {
            putExtra(BARCODE_KEY, barcode)
        }
        startActivity(intent)
    }

    // ---- Snackbar ----

    private fun showSnackbar(text: String) {
        val activity = requireActivity()
        if(activity is MainActivity) {
            activity.showSnackbar(text)
        }
    }

    // ---- Intent ----

    private fun sendResultToAppIntent(intent: Intent) {
        requireActivity().apply {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}