package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.common.extensions.convertToString
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixSmsBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.SMSParsedResult

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixSmsFragment : AbstractBarcodeMatrixFragment() {
    private var _binding: FragmentBarcodeMatrixSmsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixSmsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is SMSParsedResult && parsedResult.type == ParsedResultType.SMS) {
            val phoneNumberView = viewBinding.fragmentBarcodeMatrixSmsNumberLayout
            val subjectView = viewBinding.fragmentBarcodeMatrixSmsSubjectLayout
            val bodyView = viewBinding.fragmentBarcodeMatrixSmsBodyLayout

            phoneNumberView.setContentsText(parsedResult.numbers?.convertToString())
            subjectView.setContentsText(parsedResult.subject)
            bodyView.setContentsText(parsedResult.body)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }
}