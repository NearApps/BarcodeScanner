package com.github.nearapps.barcodescanner.presentation.views.fragments.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.lifecycle.LifecycleOwner
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT_ERROR_CORRECTION_LEVEL
import com.github.nearapps.barcodescanner.common.extensions.SCAN_RESULT_FORMAT
import com.github.nearapps.barcodescanner.common.extensions.toIntent
import com.github.nearapps.barcodescanner.common.utils.BARCODE_KEY
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING
import com.github.nearapps.barcodescanner.databinding.FragmentMainCameraXScannerBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.domain.library.BeepManager
import com.github.nearapps.barcodescanner.domain.library.SettingsManager
import com.github.nearapps.barcodescanner.domain.library.VibratorAppCompat
import com.github.nearapps.barcodescanner.domain.library.camera.AbstractCameraXBarcodeAnalyzer
import com.github.nearapps.barcodescanner.domain.library.camera.CameraConfig
import com.github.nearapps.barcodescanner.domain.library.camera.CameraXBarcodeAnalyzer
import com.github.nearapps.barcodescanner.domain.library.camera.CameraXBarcodeLegacyAnalyzer
import com.github.nearapps.barcodescanner.domain.library.camera.CameraZoomGestureDetector
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeAnalysisActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeScanFromImageGalleryActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.BaseActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.MainActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment
import com.google.zxing.Result
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

/**
 * A simple [Fragment] subclass.
 */
class MainCameraXScannerFragment : BaseFragment(), AbstractCameraXBarcodeAnalyzer.BarcodeDetector {

    companion object {
        private const val ZXING_SCAN_INTENT_ACTION = "com.google.zxing.client.android.SCAN"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private var cameraConfig: CameraConfig? = null
    private val databaseBarcodeViewModel: DatabaseBarcodeViewModel by activityViewModel()

    // ---- View ----
    private var _binding: FragmentMainCameraXScannerBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureResultBarcodeScanFromImageActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainCameraXScannerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()
        askPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraConfig?.stopCamera()
        _binding=null
    }

    override fun onResume() {
        super.onResume()

        if (allPermissionsGranted()) {
            doPermissionGranted()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = requireActivity()
        if(activity is BaseActivity) {
            if(activity.settingsManager.isAutoScreenRotationScanDisabled) {
                activity.lockDeviceRotation(true)
            }
        }
    }

    // ---- Menu ----

    private fun configureMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_scanner, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId) {
                R.id.menu_scanner_flash -> {
                    cameraConfig?.switchFlash()
                    requireActivity().invalidateOptionsMenu()
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

                if(cameraConfig?.hasFlash()==true && allPermissionsGranted()) {
                    if (cameraConfig?.flashEnabled == true) {
                        menu.getItem(0).icon =
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.baseline_flash_on_24
                            )
                    } else {
                        menu.getItem(0).icon =
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.baseline_flash_off_24
                            )
                    }

                } else {
                    menu.getItem(0).isVisible = false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // ---- Camera Permission ----

    private fun askPermission() {
        if (!allPermissionsGranted()) {
            // Gère le resultat de la demande de permission d'accès à la caméra.
            val requestPermission: ActivityResultLauncher<String> =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    if (it) {
                        doPermissionGranted()
                    } else doPermissionRefused()
                }
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun allPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireActivity(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun doPermissionGranted() {
        configureCamera()
        viewBinding.fragmentMainCameraXScannerCameraPermissionTextView.visibility = View.GONE
        viewBinding.fragmentMainCameraXScannerPreviewView.visibility = View.VISIBLE
        viewBinding.fragmentMainCameraXScannerScanOverlay.visibility = View.VISIBLE
        viewBinding.fragmentMainCameraXScannerSlider.visibility = View.VISIBLE
        viewBinding.fragmentMainCameraXScannerInformationTextView?.visibility = View.VISIBLE
    }

    private fun doPermissionRefused() {
        cameraConfig?.stopCamera()
        viewBinding.fragmentMainCameraXScannerCameraPermissionTextView.visibility = View.VISIBLE
        viewBinding.fragmentMainCameraXScannerPreviewView.visibility = View.GONE
        viewBinding.fragmentMainCameraXScannerScanOverlay.visibility = View.GONE
        viewBinding.fragmentMainCameraXScannerSlider.visibility = View.GONE
        viewBinding.fragmentMainCameraXScannerInformationTextView?.visibility = View.GONE
    }

    // ---- Camera ----

    private fun configureCamera() {
        cameraConfig = CameraConfig(requireContext()).apply {

            val analyzer: AbstractCameraXBarcodeAnalyzer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraXBarcodeAnalyzer(this@MainCameraXScannerFragment)
            } else {
                CameraXBarcodeLegacyAnalyzer(this@MainCameraXScannerFragment)
            }

            this.setAnalyzer(analyzer)
            this.startCamera(
                lifecycleOwner = this@MainCameraXScannerFragment as LifecycleOwner,
                previewView = viewBinding.fragmentMainCameraXScannerPreviewView
            )
            this@MainCameraXScannerFragment.configureZoom(this)
        }
    }

    override fun onBarcodeFound(result: Result) {
        viewBinding.fragmentMainCameraXScannerPreviewView.post {
            if(cameraConfig?.isRunning() == true) {
                cameraConfig?.stopCamera()
                onSuccessfulScanFromCamera(result)
            }
        }
    }

    override fun onError(msg: String) {
        viewBinding.fragmentMainCameraXScannerPreviewView.post {
            cameraConfig?.stopCamera()
            viewBinding.fragmentMainCameraXScannerCameraPermissionTextView.text = getString(R.string.scan_error_exception_label, msg)
            doPermissionRefused()
        }
    }

    private fun configureZoom(cameraConfig: CameraConfig) {
        val slider = viewBinding.fragmentMainCameraXScannerSlider
        slider.value = get<SettingsManager>().getDefaultZoomValue()/100f
        cameraConfig.setLinearZoom(slider.value)
        slider.addOnChangeListener { v, value, _ ->
            cameraConfig.setLinearZoom(value)
            // BZZZTT!!1!
            v.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        }
        CameraZoomGestureDetector(slider.value)
            .attach(viewBinding.fragmentMainCameraXScannerScanOverlay) { value ->
                slider.value = value
            }
    }

    // ---- Scan successful ----

    private inline fun onSuccessfulScan(
        contents: String?,
        formatName: String?,
        errorCorrectionLevel: QrCodeErrorCorrectionLevel,
        crossinline onResult: (barcode: Barcode) -> Unit
    ) = requireActivity().runOnUiThread {

        if(contents != null && formatName != null) {

            val settingsManager = get<SettingsManager>()

            if(settingsManager.shouldCopyBarcodeScan) {
                copyToClipboard("contents", contents)
                showToastText(R.string.barcode_copied_label)
            }

            if(settingsManager.useBipScan)
                get<BeepManager>().playBeepSound(requireActivity())

            if(settingsManager.useVibrateScan)
                get<VibratorAppCompat>().vibrate()

            val barcode: Barcode = get { parametersOf(contents, formatName, errorCorrectionLevel) }

            if(settingsManager.shouldAddBarcodeScanToHistory) {
                // Insert les informations du code-barres dans la base de données (de manière asynchrone)
                databaseBarcodeViewModel.insertBarcode(barcode)
            }

            onResult(barcode)
        } else {
            showSnackbar(getString(R.string.scan_cancel_label))
        }
    }

    // ---- Scan from Camera ----

    private fun onSuccessfulScanFromCamera(result: Result) {
        val contents = result.text
        val formatName = result.barcodeFormat?.name
        val errorCorrectionLevel: QrCodeErrorCorrectionLevel =
            get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT)) { parametersOf(result) }

        onSuccessfulScan(contents, formatName, errorCorrectionLevel) { barcode ->
            // Si l'application a été ouverte via une application tierce
            if (requireActivity().intent?.action == ZXING_SCAN_INTENT_ACTION) {
                sendResultToAppIntent(result.toIntent())
            } else {
                startBarcodeAnalysisActivity(barcode)
            }
        }
    }

    /**
     * Démarre l'Activity: BarcodeAnalysisActivity.
     */
    private fun startBarcodeAnalysisActivity(barcode: Barcode) {
        val intent = createStartActivityIntent(requireContext(), BarcodeAnalysisActivity::class).apply {
            putExtra(BARCODE_KEY, barcode)
        }
        startActivity(intent)
    }

    // ---- Scan from Image ----

    private var resultBarcodeScanFromImageActivity: ActivityResultLauncher<Intent>? = null

    private fun configureResultBarcodeScanFromImageActivity(){
        resultBarcodeScanFromImageActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                it.data?.let { intentResult ->
                    onSuccessfulScanFromImage(intentResult)
                }
            }
        }
    }

    private fun onSuccessfulScanFromImage(intentResult: Intent) {
        val contents = intentResult.getStringExtra(SCAN_RESULT)
        val formatName = intentResult.getStringExtra(SCAN_RESULT_FORMAT)
        val errorCorrectionLevel: QrCodeErrorCorrectionLevel =
            get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING)) {
                parametersOf(intentResult.getStringExtra(SCAN_RESULT_ERROR_CORRECTION_LEVEL))
            }

        onSuccessfulScan(contents, formatName, errorCorrectionLevel) { barcode ->
            // Si l'application a été ouverte via une application tierce
            if (requireActivity().intent?.action == ZXING_SCAN_INTENT_ACTION) {
                sendResultToAppIntent(intentResult)
            } else {
                startBarcodeAnalysisActivity(barcode)
            }
        }
    }

    private fun startBarcodeScanFromImageActivity() {
        cameraConfig?.stopCamera()
        resultBarcodeScanFromImageActivity?.let { result ->
            val intent = createStartActivityIntent(requireContext(), BarcodeScanFromImageGalleryActivity::class)
            result.launch(intent)
        }
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