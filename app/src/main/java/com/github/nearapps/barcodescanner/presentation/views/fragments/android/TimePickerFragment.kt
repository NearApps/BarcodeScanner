package com.github.nearapps.barcodescanner.presentation.views.fragments.android

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var callback: (time: String) -> Unit
    private lateinit var simpleDateFormat: SimpleDateFormat

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), this, hour, minute, DateFormat.is24HourFormat(requireContext()))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val formattedTime = simpleDateFormat.format(calendar.time)
        callback(formattedTime)
    }

    companion object {
        fun newInstance(callback: (time: String) -> Unit, simpleDateFormat: SimpleDateFormat): TimePickerFragment {
            return TimePickerFragment().apply {
                this.callback = callback
                this.simpleDateFormat = simpleDateFormat
            }
        }
    }
}