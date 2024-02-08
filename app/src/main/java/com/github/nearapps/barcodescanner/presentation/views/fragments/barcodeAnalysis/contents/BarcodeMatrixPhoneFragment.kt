package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixPhoneBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.TelParsedResult

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixPhoneFragment : AbstractBarcodeMatrixFragment() {
    private var _binding: FragmentBarcodeMatrixPhoneBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixPhoneBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is TelParsedResult && parsedResult.type == ParsedResultType.TEL) {
            viewBinding.fragmentBarcodeMatrixPhoneNumberView.setContentsText(parsedResult.number)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }
}