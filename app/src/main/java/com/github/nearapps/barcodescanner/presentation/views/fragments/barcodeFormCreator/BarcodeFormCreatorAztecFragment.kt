package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorAztecFragment: AbstractBarcodeFormCreatorBasicFragment() {
    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkBlankError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.AZTEC
}