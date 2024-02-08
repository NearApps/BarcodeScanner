package com.github.nearapps.barcodescanner.presentation.customView.preferences

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreferenceCompat
import com.github.nearapps.barcodescanner.common.extensions.initializeCustomResourcesValues

class CustomSwitchPreferenceCompat: SwitchPreferenceCompat {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        initializeCustomResourcesValues(holder)

        val titleView = holder.findViewById(android.R.id.title) as TextView
        titleView.setLineSpacing(0f, 1f)
        val summaryView = holder.findViewById(android.R.id.summary) as TextView
        summaryView.setLineSpacing(0f, 1f)
    }
}