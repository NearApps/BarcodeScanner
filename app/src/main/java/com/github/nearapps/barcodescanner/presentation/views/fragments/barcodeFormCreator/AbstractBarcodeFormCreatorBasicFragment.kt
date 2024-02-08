package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel

/**
 * A simple [Fragment] subclass.
 */
abstract class AbstractBarcodeFormCreatorBasicFragment: AbstractBarcodeFormCreatorFragment() {

    private var _binding: FragmentBarcodeFormCreatorBinding? = null
    protected val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getBarcodeTextFromForm(): String {
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorTextInputEditText
        hideSoftKeyboard()
        return inputEditText.text.toString()
    }

    override fun getQrCodeErrorCorrectionLevel(): QrCodeErrorCorrectionLevel = QrCodeErrorCorrectionLevel.NONE
}