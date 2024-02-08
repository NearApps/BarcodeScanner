package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorCodabarFragment: AbstractBarcodeFormCreatorBasicFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorTextInputEditText
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkCodabarError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.CODABAR
}