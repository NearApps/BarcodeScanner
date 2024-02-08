package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrUrlBinding

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrUrlFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrUrlBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrUrlBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getBarcodeTextFromForm(): String {
        val inputEditText = viewBinding.fragmentBarcodeFormCreatorQrUrlInputEditText
        hideSoftKeyboard()
        return inputEditText.text.toString()
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkQrUrlError(it) }
    }
}