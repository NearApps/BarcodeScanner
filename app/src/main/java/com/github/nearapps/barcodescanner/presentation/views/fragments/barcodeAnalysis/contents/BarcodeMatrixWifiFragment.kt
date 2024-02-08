package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixWifiBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.WifiParsedResult

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixWifiFragment : AbstractBarcodeMatrixFragment() {
    private var _binding: FragmentBarcodeMatrixWifiBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixWifiBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is WifiParsedResult && parsedResult.type == ParsedResultType.WIFI) {
            val ssidView = viewBinding.fragmentBarcodeMatrixWifiSsidLayout
            val passwordView = viewBinding.fragmentBarcodeMatrixWifiPasswordLayout
            val encryptionView = viewBinding.fragmentBarcodeMatrixWifiEncryptionLayout
            val isHiddenView = viewBinding.fragmentBarcodeMatrixWifiIsHiddenLayout

            ssidView.setContentsText(parsedResult.ssid)
            passwordView.setContentsText(parsedResult.password)
            encryptionView.setContentsText(parsedResult.networkEncryption)
            isHiddenView.setContentsText(if(isHidden) getString(R.string.yes_label) else getString(R.string.no_label))
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }
}