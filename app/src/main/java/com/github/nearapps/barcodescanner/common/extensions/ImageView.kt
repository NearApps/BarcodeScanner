package com.github.nearapps.barcodescanner.common.extensions

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import coil.load

fun ImageView.setImageFromWeb(url: String?, layout: View? = null){
    if(url.isNullOrBlank()){
        layout?.visibility = View.GONE
        this.visibility = View.GONE
    } else {
        this.load(url)
    }
}

fun ImageView.setImageColorFromAttrRes(@AttrRes attrRes: Int){
    @ColorInt val color = context.getColorInt(attrRes)
    imageTintList = ColorStateList.valueOf(color)
}