package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixUpiParsedBinding
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment

class BarcodeMatrixUpiParsedFragment : BaseFragment() {

    private var uri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uri = it.getString(URI_BUNDLE_KEY)
        }
    }

    private var _binding: FragmentBarcodeMatrixUpiParsedBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixUpiParsedBinding.inflate(inflater, container, false)
        if (uri?.startsWith("upi") != true) {
            viewBinding.root.visibility = View.GONE
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uri?.let {
            val uriParsed: Uri = Uri.parse(uri)

            val upiIdView = viewBinding.fragmentBarcodeMatrixUpiParsedUpiIdLayout
            val payeeNameView = viewBinding.fragmentBarcodeMatrixUpiParsedPayeeNameLayout
            val amountView = viewBinding.fragmentBarcodeMatrixUpiParsedAmountLayout
            val currencyView = viewBinding.fragmentBarcodeMatrixUpiParsedCurrencyLayout
            val descriptionView = viewBinding.fragmentBarcodeMatrixUpiParsedDescriptionLayout

            upiIdView.setContentsText(uriParsed.getQueryParameter("pa"))
            payeeNameView.setContentsText(uriParsed.getQueryParameter("pn"))
            amountView.setContentsText(uriParsed.getQueryParameter("am"))
            currencyView.setContentsText(uriParsed.getQueryParameter("cu"))
            descriptionView.setContentsText(uriParsed.getQueryParameter("tn"))
        } ?: run {
            viewBinding.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    companion object {
        private const val URI_BUNDLE_KEY = "uriBundleKey"

        @JvmStatic
        fun newInstance(uri: String) =
            BarcodeMatrixUpiParsedFragment().apply {
                arguments = Bundle().apply {
                    putString(URI_BUNDLE_KEY, uri)
                }
            }
    }
}