package com.github.nearapps.barcodescanner.presentation.views.fragments.android

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var callback: (date: String) -> Unit
    private lateinit var simpleDateFormat: SimpleDateFormat

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val formattedDate = simpleDateFormat.format(calendar.time)
        callback(formattedDate)
    }

    companion object {
        fun newInstance(callback: (date: String) -> Unit, simpleDateFormat: SimpleDateFormat): DatePickerFragment {
            return DatePickerFragment().apply {
                this.callback = callback
                this.simpleDateFormat = simpleDateFormat
            }
        }
    }
}