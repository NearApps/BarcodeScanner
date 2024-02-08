package com.github.nearapps.barcodescanner.presentation.customView.preferences

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceViewHolder
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.setTextColorFromAttrRes

class CustomPreferenceCategory: PreferenceCategory {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        val font: Typeface? = Typeface.create("sans-serif-black", Typeface.NORMAL)//ResourcesCompat.getFont(context, R.font.roboto_black)
        val textSizeInDP = context.resources.getDimension(R.dimen.sub_title_text_size) / context.resources.displayMetrics.density

        val titleView: TextView? = holder.itemView.findViewById(android.R.id.title)
        titleView?.typeface = font
        titleView?.textSize = textSizeInDP
        titleView?.setTextColorFromAttrRes(R.attr.colorPrimary)
    }
}