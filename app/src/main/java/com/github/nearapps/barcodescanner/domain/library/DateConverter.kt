package com.github.nearapps.barcodescanner.domain.library

import android.content.Context
import android.text.format.DateFormat
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

/**
 * Convertit une date au format "yyyy-MM-dd" dans le format de date correspondant à la langue de l'appareil.
 */
class DateConverter: KoinComponent {
    fun convertDateToLocalizedFormat(context: Context, dateString: String?, format: String = "yyyy-MM-dd"): String? {
        if(dateString != null) {
            val inputFormat: SimpleDateFormat = get { parametersOf(format) }
            try {
                val date = inputFormat.parse(dateString)
                if (date != null) {
                    val outputFormat = DateFormat.getDateFormat(context)
                    return outputFormat.format(date)
                }
            } catch (e: Exception) {
                return dateString
            }
        }
        return dateString
    }
}