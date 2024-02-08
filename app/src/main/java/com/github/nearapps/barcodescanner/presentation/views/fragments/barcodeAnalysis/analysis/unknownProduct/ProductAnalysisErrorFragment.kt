package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.unknownProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentProductAnalysisErrorBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.UnknownProductBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.library.InternetChecker
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class ProductAnalysisErrorFragment: BarcodeAnalysisFragment<UnknownProductBarcodeAnalysis>() {

    private var _binding: FragmentProductAnalysisErrorBinding? = null
    private val viewBinding get() = _binding!!

    private val internetChecker: InternetChecker by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProductAnalysisErrorBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: UnknownProductBarcodeAnalysis) {
        configureInformationTextViewError()
        configureMessageTextViewError(analysis.message)
    }

    private fun configureInformationTextViewError() {
        val isConnected = internetChecker.isInternetAvailable()

        val msg = if(!isConnected)
            getString(R.string.scan_error_internet_information_label)
        else getString(R.string.scan_error_information_label)

        viewBinding.fragmentProductAnalysisErrorInformationTextView.text = msg
    }

    private fun configureMessageTextViewError(message: String?) = displayText(
        textView = viewBinding.fragmentProductAnalysisErrorMessageTextView,
        layout = viewBinding.fragmentProductAnalysisErrorMessageLayout,
        text = message
    )
}