package com.github.nearapps.barcodescanner.common.extensions

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.drawRectangle(left: Float, top: Float, right: Float, bottom: Float, paint: Paint) {
    drawRect(left, top, right, bottom, paint)
}

fun Canvas.drawRoundRectangle(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paint: Paint) {
    drawRoundRect(left, top, right, bottom, rx, ry, paint)
}