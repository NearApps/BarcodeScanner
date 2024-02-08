package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrAgendaBinding
import com.github.nearapps.barcodescanner.domain.library.VEventBuilder
import com.github.nearapps.barcodescanner.presentation.views.fragments.android.DatePickerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.android.TimePickerFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Date

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrAgendaFragment : AbstractBarcodeFormCreatorQrFragment() {

    private val date: Date = get()
    private val simpleDateTimeFormat: SimpleDateFormat = get { parametersOf("yyyyMMdd'T'HHmmss'Z'") }
    private val simpleDateFormat: SimpleDateFormat = get { parametersOf("yyyy-MM-dd") }
    private val simpleTimeFormat: SimpleDateFormat = get { parametersOf("HH:mm z") }

    private var _binding: FragmentBarcodeFormCreatorQrAgendaBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrAgendaBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.text = simpleDateFormat.format(date)
        viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.text = simpleTimeFormat.format(date)
        viewBinding.fragmentBarcodeFormCreatorQrAgendaEndDatePicker.text = viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.text
        viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.text = viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.text

        configureOnClickAllOfDayCheckBox(viewBinding.fragmentBarcodeFormCreatorQrAgendaAllOfDayCheckBox)
        configureOnClickDateTimePicker()
    }

    override fun getBarcodeTextFromForm(): String {
        val dtStart: String
        val dtEnd: String

        if(viewBinding.fragmentBarcodeFormCreatorQrAgendaAllOfDayCheckBox.isChecked) {
            dtStart = viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.text.toString().replace("-", "")
            dtEnd = viewBinding.fragmentBarcodeFormCreatorQrAgendaEndDatePicker.text.toString().replace("-", "")
        } else {
            val dateStartStr = viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.text.toString()
            val dateEndStr = viewBinding.fragmentBarcodeFormCreatorQrAgendaEndDatePicker.text.toString()
            val timeStartStr = viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.text.toString()
            val timeEndStr = viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.text.toString()

            val dateStartTimestamp = simpleDateFormat.parse(dateStartStr)?.time ?: 0L
            val dateEndTimestamp = simpleDateFormat.parse(dateEndStr)?.time ?: 0L
            val timeStartTimestamp = simpleTimeFormat.parse(timeStartStr)?.time ?: 0L
            val timeEndTimestamp = simpleTimeFormat.parse(timeEndStr)?.time ?: 0L

            dtStart = simpleDateTimeFormat.format(dateStartTimestamp+timeStartTimestamp)
            dtEnd = simpleDateTimeFormat.format(dateEndTimestamp+timeEndTimestamp)
        }

        return VEventBuilder().apply {
            setSummary(viewBinding.fragmentBarcodeFormCreatorQrAgendaSummaryInputEditText.text.toString())
            setDtStart(dtStart)
            setDtEnd(dtEnd)
            setLocation(viewBinding.fragmentBarcodeFormCreatorQrAgendaPlaceInputEditText.text.toString())
            setDescription(viewBinding.fragmentBarcodeFormCreatorQrAgendaDescriptionInputEditText.text.toString())
        }.build()
    }

    private fun configureOnClickAllOfDayCheckBox(checkBox: CheckBox) {
        checkBox.setOnClickListener {
            if(checkBox.isChecked) {
                viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.visibility=View.GONE
                viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.visibility=View.GONE
            } else {
                viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.visibility=View.VISIBLE
                viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.visibility=View.VISIBLE
            }
        }
    }

    // ---- Date Time Picker ----

    private val beginDatePickerFragment: DatePickerFragment by lazy {
        DatePickerFragment.newInstance(
            { viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.text = it },
            simpleDateFormat
        )
    }

    private val endDatePickerFragment: DatePickerFragment by lazy {
        DatePickerFragment.newInstance(
            { viewBinding.fragmentBarcodeFormCreatorQrAgendaEndDatePicker.text = it },
            simpleDateFormat
        )
    }

    private val beginTimePickerFragment: TimePickerFragment by lazy {
        TimePickerFragment.newInstance(
            { viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.text = it },
            simpleTimeFormat
        )
    }

    private val endTimePickerFragment: TimePickerFragment by lazy {
        TimePickerFragment.newInstance(
            { viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.text = it },
            simpleTimeFormat
        )
    }

    private fun configureOnClickDateTimePicker(){
        // Begin Date
        viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginDatePicker.setOnClickListener {
            beginDatePickerFragment.show(requireActivity().supportFragmentManager, "beginDateTag")
        }

        // BeginTime
        viewBinding.fragmentBarcodeFormCreatorQrAgendaBeginTimePicker.setOnClickListener {
            if(!viewBinding.fragmentBarcodeFormCreatorQrAgendaAllOfDayCheckBox.isChecked)
                beginTimePickerFragment.show(requireActivity().supportFragmentManager, "beginTimeTag")
        }

        // End Date
        viewBinding.fragmentBarcodeFormCreatorQrAgendaEndDatePicker.setOnClickListener {
            endDatePickerFragment.show(requireActivity().supportFragmentManager, "endDateTag")
        }

        // End Time
        viewBinding.fragmentBarcodeFormCreatorQrAgendaEndTimePicker.setOnClickListener {
            if(!viewBinding.fragmentBarcodeFormCreatorQrAgendaAllOfDayCheckBox.isChecked)
                endTimePickerFragment.show(requireActivity().supportFragmentManager, "endTimeTag")
        }
    }
}