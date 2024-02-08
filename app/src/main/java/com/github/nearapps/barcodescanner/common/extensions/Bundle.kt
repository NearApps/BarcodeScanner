package com.github.nearapps.barcodescanner.common.extensions

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T : Serializable?> Bundle.serializable(key: String?, clazz: Class<T>): T? = when {
    Build.VERSION.SDK_INT >= 33 -> this.getSerializable(key, clazz)
    else -> @Suppress("DEPRECATION") clazz.cast(this.getSerializable(key))
}