package com.github.nearapps.barcodescanner.common.extensions

import android.graphics.Typeface
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.github.nearapps.barcodescanner.R

fun Preference.initializeCustomResourcesValues(holder: PreferenceViewHolder?) {

    //val font: Typeface? = ResourcesCompat.getFont(context, R.font.roboto_medium)
    val primaryTextSizeInDP = context.resources.getDimension(R.dimen.sub_title_text_size) / context.resources.displayMetrics.density

    val titleView: TextView? = holder?.itemView?.findViewById(android.R.id.title)
    titleView?.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)//Typeface.SANS_SERIF
    titleView?.textSize = primaryTextSizeInDP

    val secondaryTextSizeInDP = context.resources.getDimension(R.dimen.standard_text_size) / context.resources.displayMetrics.density
    val summaryView: TextView? = holder?.itemView?.findViewById(android.R.id.summary)
    summaryView?.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)//Typeface.SANS_SERIF
    summaryView?.textSize = secondaryTextSizeInDP
}