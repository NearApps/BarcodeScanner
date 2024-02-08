package com.github.nearapps.barcodescanner.common.extensions

import android.content.res.ColorStateList
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.google.android.material.chip.Chip

fun Chip.setChipBackgroundColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    this.chipBackgroundColor = ColorStateList.valueOf(color)
}

fun Chip.setChipTextColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    setTextColor(ColorStateList.valueOf(color))
}

fun Chip.setChipStrokeColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    chipStrokeColor = ColorStateList.valueOf(color)
}

fun Chip.setChipIconTintFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    chipIconTint = ColorStateList.valueOf(color)
}