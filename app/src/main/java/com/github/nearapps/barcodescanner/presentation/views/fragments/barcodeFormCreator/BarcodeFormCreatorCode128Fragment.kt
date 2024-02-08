package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import com.github.nearapps.barcodescanner.common.utils.CODE_128_LENGTH
import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorCode128Fragment: AbstractBarcodeFormCreatorBasicFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorTextInputEditText
        inputEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(CODE_128_LENGTH))
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkCode128Error(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.CODE_128
}