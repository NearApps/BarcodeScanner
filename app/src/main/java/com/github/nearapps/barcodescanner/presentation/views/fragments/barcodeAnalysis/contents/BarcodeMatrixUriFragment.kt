package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixUriBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.URIParsedResult

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixUriFragment: AbstractBarcodeMatrixFragment() {

    private var _binding: FragmentBarcodeMatrixUriBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixUriBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is URIParsedResult && parsedResult.type == ParsedResultType.URI) {
            val uri = parsedResult.uri
            viewBinding.fragmentBarcodeMatrixUriUrlLayout.setContentsText(uri)
            configureIsPossiblyMaliciousURI(parsedResult.isPossiblyMaliciousURI)
            if(uri.startsWith("upi")) {
                applyFragment(
                    containerViewId = viewBinding.fragmentBarcodeMatrixUriParsedLayout.id,
                    fragment = BarcodeMatrixUpiParsedFragment.newInstance(uri)
                )
            } else {
                viewBinding.fragmentBarcodeMatrixUriParsedLayout.visibility = View.GONE
            }
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }

    private fun configureIsPossiblyMaliciousURI(isPossiblyMaliciousURI: Boolean?) {
        if (isPossiblyMaliciousURI != true) {
            viewBinding.fragmentBarcodeMatrixUriMaliciousLayout.visibility = View.GONE
        }
    }
}