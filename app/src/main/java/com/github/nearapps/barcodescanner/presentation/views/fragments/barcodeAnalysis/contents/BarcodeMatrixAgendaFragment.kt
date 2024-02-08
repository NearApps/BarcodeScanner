package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeMatrixAgendaBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.google.zxing.client.result.CalendarParsedResult
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 */
class BarcodeMatrixAgendaFragment : AbstractBarcodeMatrixFragment() {

    private val simpleDateFormat: SimpleDateFormat by inject { parametersOf("E dd MMM yyyy HH:mm") }

    private var _binding: FragmentBarcodeMatrixAgendaBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeMatrixAgendaBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(product: BarcodeAnalysis, parsedResult: ParsedResult) {
        if(parsedResult is CalendarParsedResult && parsedResult.type == ParsedResultType.CALENDAR) {
            val nameEventView = viewBinding.fragmentBarcodeMatrixAgendaNameEventLayout
            val startEventView = viewBinding.fragmentBarcodeMatrixAgendaStartDateLayout
            val endEventView = viewBinding.fragmentBarcodeMatrixAgendaEndDateLayout
            val placeView = viewBinding.fragmentBarcodeMatrixAgendaPlaceLayout
            val descriptionView = viewBinding.fragmentBarcodeMatrixAgendaDescriptionLayout

            nameEventView.setContentsText(parsedResult.summary)
            startEventView.setContentsText(getDateFormat(parsedResult.startTimestamp))
            endEventView.setContentsText(getDateFormat(parsedResult.endTimestamp))
            placeView.setContentsText(parsedResult.location)
            descriptionView.setContentsText(parsedResult.description)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }

    private fun getDateFormat(date: Long?): String? = if(date!=null) {
        simpleDateFormat.format(date)
    } else null
}