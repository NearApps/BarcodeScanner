package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixLocalisationBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.GeoParsedResult
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixLocalisationFragment : AbstractBarcodeMatrixFragment() {

    private var _binding: FragmentBarcodeMatrixLocalisationBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixLocalisationBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is GeoParsedResult && parsedResult.type == ParsedResultType.GEO) {
            val latitudeView = viewBinding.fragmentBarcodeMatrixLocalisationLatitudeLayout
            val longitudeView = viewBinding.fragmentBarcodeMatrixLocalisationLongitudeLayout
            val altitudeView = viewBinding.fragmentBarcodeMatrixLocalisationAltitudeLayout
            val queryView = viewBinding.fragmentBarcodeMatrixLocalisationQueryLayout

            latitudeView.setContentsText(parsedResult.latitude.toString())
            longitudeView.setContentsText(parsedResult.longitude.toString())
            altitudeView.setContentsText(parsedResult.altitude.toString())
            queryView.setContentsText(parsedResult.query)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }
}