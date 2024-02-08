package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.common.extensions.convertToString
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixEmailBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.EmailAddressParsedResult
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixEmailFragment : AbstractBarcodeMatrixFragment() {
    private var _binding: FragmentBarcodeMatrixEmailBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixEmailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is EmailAddressParsedResult && parsedResult.type == ParsedResultType.EMAIL_ADDRESS) {
            val emailAddressView = viewBinding.fragmentBarcodeMatrixEmailAddressLayout
            val ccView = viewBinding.fragmentBarcodeMatrixEmailCcLayout
            val bccView = viewBinding.fragmentBarcodeMatrixEmailBccLayout
            val subjectView = viewBinding.fragmentBarcodeMatrixEmailSubjectLayout
            val bodyView = viewBinding.fragmentBarcodeMatrixEmailBodyLayout

            emailAddressView.setContentsText(parsedResult.tos?.convertToString())
            ccView.setContentsText(parsedResult.cCs?.convertToString())
            bccView.setContentsText(parsedResult.bcCs?.convertToString())
            subjectView.setContentsText(parsedResult.subject)
            bodyView.setContentsText(parsedResult.body)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }
}