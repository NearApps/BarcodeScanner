package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrSmsBinding

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrSmsFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrSmsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrSmsBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getBarcodeTextFromForm(): String {
        val number = viewBinding.fragmentBarcodeFormCreatorQrSmsPhoneNumberInputEditText.text.toString()
        val message = viewBinding.fragmentBarcodeFormCreatorQrSmsMessageInputEditText.text.toString()

        val qrText = when {
            number.isNotBlank() && message.isNotBlank() -> "smsto:$number:$message"
            number.isNotBlank() -> "tel:$number" // -> Si pas de message, on propose juste le numéro de téléphone
            else -> ""
        }

        return qrText
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkQrPhoneNumberError(it) }
    }
}