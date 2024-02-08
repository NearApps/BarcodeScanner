package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorDataMatrixFragment: AbstractBarcodeFormCreatorBasicFragment() {
    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkDataMatrixError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.DATA_MATRIX
}