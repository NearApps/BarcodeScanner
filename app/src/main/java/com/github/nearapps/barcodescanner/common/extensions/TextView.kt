package com.github.nearapps.barcodescanner.common.extensions

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun TextView.setTextColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    setTextColor(ColorStateList.valueOf(color))
}

/*fun TextView.setTextIsSelectableCompat(value: Boolean) {
    //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        setTextIsSelectable(value)
    //}
}*/