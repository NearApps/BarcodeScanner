package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrMailBinding

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrMailFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrMailBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrMailBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getBarcodeTextFromForm(): String {
        val email = viewBinding.fragmentBarcodeFormCreatorQrMailToInputEditText.text.toString()
        val subject = viewBinding.fragmentBarcodeFormCreatorQrMailSubjectInputEditText.text.toString()
        val message = viewBinding.fragmentBarcodeFormCreatorQrMailMessageInputEditText.text.toString()

        return if(email.isBlank() && subject.isBlank() && message.isBlank())
            "" else "MATMSG:TO:$email;SUB:$subject;BODY:$message;;"
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkQrMailError(it) }
    }
}