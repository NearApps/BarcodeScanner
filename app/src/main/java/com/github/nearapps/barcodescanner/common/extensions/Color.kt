package com.github.nearapps.barcodescanner.common.extensions

import android.graphics.Color
import androidx.annotation.ColorInt

fun @receiver:ColorInt Int.toColorHex(): String {
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return String.format("#%02X%02X%02X", red, green, blue)
}

fun @receiver:ColorInt Int.toColorAlpha(): Float = Color.alpha(this) / 255f