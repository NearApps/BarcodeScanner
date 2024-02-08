package com.github.nearapps.barcodescanner.common.extensions

import android.widget.Button
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun Button.setTextColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    this.setTextColor(color)
}