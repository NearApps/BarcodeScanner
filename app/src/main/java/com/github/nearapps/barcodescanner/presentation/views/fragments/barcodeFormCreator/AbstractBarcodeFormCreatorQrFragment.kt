package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.domain.library.SettingsManager
import com.google.zxing.BarcodeFormat
import org.koin.android.ext.android.get

abstract class AbstractBarcodeFormCreatorQrFragment: AbstractBarcodeFormCreatorFragment() {
    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkBlankError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.QR_CODE

    override fun getQrCodeErrorCorrectionLevel(): QrCodeErrorCorrectionLevel =
        get<SettingsManager>().getQrCodeErrorCorrectionLevel()
}