package com.github.nearapps.barcodescanner.common.extensions

import android.content.Context
import android.net.Uri

fun Uri.read(context: Context): String {
    var text = ""
    try {
        val inputStream = context.contentResolver.openInputStream(this)
        text = inputStream?.readBytes()?.toString(Charsets.UTF_8) ?: ""
        inputStream?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return text
}