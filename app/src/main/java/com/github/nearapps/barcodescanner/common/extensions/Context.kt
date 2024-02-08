package com.github.nearapps.barcodescanner.common.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}

@ColorRes
fun Context.getColorRes(@AttrRes attrRes: Int): Int {
    val resolvedAttr = resolveThemeAttr(attrRes)
    // resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
    return if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
}

@ColorInt
fun Context.getColorInt(@AttrRes attrRes: Int): Int {
    @ColorRes val colorRes = getColorRes(attrRes)
    return ContextCompat.getColor(this, colorRes)
}


fun Context.getColorStateListFromAttrRes(@AttrRes attrRes: Int): ColorStateList {
    @ColorRes val colorRes = getColorRes(attrRes)
    return AppCompatResources.getColorStateList(this, colorRes)
}