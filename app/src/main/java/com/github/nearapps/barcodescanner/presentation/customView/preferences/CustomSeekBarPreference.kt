package com.github.nearapps.barcodescanner.presentation.customView.preferences

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.preference.PreferenceViewHolder
import androidx.preference.SeekBarPreference
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.getColorStateListFromAttrRes
import com.github.nearapps.barcodescanner.common.extensions.initializeCustomResourcesValues

class CustomSeekBarPreference: SeekBarPreference {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        initializeCustomResourcesValues(holder)

        val seekBar = holder.findViewById(R.id.seekbar) as SeekBar
        seekBar.thumbTintList = context.getColorStateListFromAttrRes(android.R.attr.colorPrimary)
        seekBar.progressTintList = context.getColorStateListFromAttrRes(android.R.attr.colorPrimary)
        //seekBar.progressBackgroundTintList = context.getColorStateListFromAttrRes(android.R.attr.colorButtonNormal)
    }
}