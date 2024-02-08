package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrPhoneBinding

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrPhoneFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrPhoneBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrPhoneBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getBarcodeTextFromForm(): String {
        val number = viewBinding.fragmentBarcodeFormCreatorQrPhoneInputEditText.text.toString()
        return if(number.isNotBlank()) "tel:$number" else ""
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkQrPhoneNumberError(it) }
    }
}
