package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.utils.BARCODE_CONTENTS_KEY
import com.github.nearapps.barcodescanner.common.utils.BARCODE_FORMAT_KEY
import com.github.nearapps.barcodescanner.common.utils.QR_CODE_ERROR_CORRECTION_LEVEL_KEY
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.domain.library.BarcodeFormatChecker
import com.github.nearapps.barcodescanner.domain.library.SettingsManager
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeDetailsActivity
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeFormCreatorActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment
import com.google.zxing.BarcodeFormat
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.parameter.parametersOf

abstract class AbstractBarcodeFormCreatorFragment: BaseFragment() {

    private val databaseBarcodeViewModel: DatabaseBarcodeViewModel by activityViewModel()
    protected val barcodeFormatChecker: BarcodeFormatChecker by inject()

    protected fun configureMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_activity_confirm, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId) {
                R.id.menu_activity_confirm_item -> {
                    generateBarcode()
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    protected fun startBarcodeDetailsActivity(
        contents: String,
        barcodeFormat: BarcodeFormat,
        qrCodeErrorCorrectionLevel: QrCodeErrorCorrectionLevel = QrCodeErrorCorrectionLevel.NONE
    ) {
        insertBarcodeIntoDatabase(contents, barcodeFormat, qrCodeErrorCorrectionLevel)
        val intent = createStartActivityIntent(requireContext(), BarcodeDetailsActivity::class).apply {
            putExtra(BARCODE_CONTENTS_KEY, contents)
            putExtra(BARCODE_FORMAT_KEY, barcodeFormat.name)
            putExtra(QR_CODE_ERROR_CORRECTION_LEVEL_KEY, qrCodeErrorCorrectionLevel.name)
        }
        startActivity(intent)
    }

    private fun insertBarcodeIntoDatabase(
        contents: String,
        barcodeFormat: BarcodeFormat,
        qrCodeErrorCorrectionLevel: QrCodeErrorCorrectionLevel
    ) {
        if(get<SettingsManager>().shouldAddBarcodeGenerateToHistory) {
            val barcode: Barcode = get {
                parametersOf(contents, barcodeFormat.name, qrCodeErrorCorrectionLevel)
            }
            // Insert les informations du code-barres dans la base de données (de manière asynchrone)
            databaseBarcodeViewModel.insertBarcode(barcode)
        }
    }

    protected fun configureErrorMessage(message: String) {
        val activity = requireActivity()
        if(activity is BarcodeFormCreatorActivity){
            activity.configureErrorMessage(message)
        }
    }

    protected fun hideErrorMessage() {
        val activity = requireActivity()
        if(activity is BarcodeFormCreatorActivity){
            activity.hideErrorMessage()
        }
    }

    protected fun generateBarcode() {
        val contents = getBarcodeTextFromForm()
        checkError(contents)?.let {
            configureErrorMessage(it)
        } ?: run {
            hideSoftKeyboard()
            hideErrorMessage()
            startBarcodeDetailsActivity(contents, getBarcodeFormat(), getQrCodeErrorCorrectionLevel())
        }
    }

    abstract val checkError: (contents: String) -> String?
    abstract fun getBarcodeTextFromForm(): String
    abstract fun getBarcodeFormat(): BarcodeFormat
    abstract fun getQrCodeErrorCorrectionLevel(): QrCodeErrorCorrectionLevel
}