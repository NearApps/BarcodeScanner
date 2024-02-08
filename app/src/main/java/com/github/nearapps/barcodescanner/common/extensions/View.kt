package com.github.nearapps.barcodescanner.common.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.AttrRes

fun View.setBackground(@AttrRes attrRes: Int) {
    val attrs = intArrayOf(attrRes)
    val typedArray = context.obtainStyledAttributes(attrs)

    val drawable: Drawable? = typedArray.getDrawable(0)

    if(drawable!=null)
        this.background = drawable

    typedArray.recycle()
}
/*
@ColorInt
fun View.convertAttrResToColorInt(@AttrRes attrRes: Int): Int {

    val typedValue = TypedValue()
    val theme = context.theme
    theme.resolveAttribute(attrRes, typedValue, true)

    return typedValue.data
}*/

inline fun View.afterMeasured(crossinline block: () -> Unit) {
    if (measuredWidth > 0 && measuredHeight > 0) {
        block()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    block()
                }
            }
        })
    }
}