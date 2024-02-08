package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import com.github.nearapps.barcodescanner.common.utils.UPC_A_LENGTH
import com.google.zxing.BarcodeFormat

class BarcodeFormCreatorUPCAFragment: AbstractBarcodeFormCreatorBasicFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorTextInputEditText
        inputEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(UPC_A_LENGTH))
        inputEditText.inputType = InputType.TYPE_CLASS_NUMBER
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkUPCAError(it) }
    }

    override fun getBarcodeFormat(): BarcodeFormat = BarcodeFormat.UPC_A
}