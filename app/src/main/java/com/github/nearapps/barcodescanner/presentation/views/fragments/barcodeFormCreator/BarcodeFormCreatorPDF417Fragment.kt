package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import com.github.nearapps.barcodescanner.common.utils.PDF_417_LENGTH
import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorPDF417Fragment: AbstractBarcodeFormCreatorBasicFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorTextInputEditText
        inputEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(PDF_417_LENGTH))
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkBlankError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.PDF_417
}