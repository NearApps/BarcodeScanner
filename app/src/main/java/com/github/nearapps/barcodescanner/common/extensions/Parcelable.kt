package com.github.nearapps.barcodescanner.common.extensions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun <T : Parcelable?> Bundle.parcelable(key: String?, clazz: Class<T>): T? = when {
    Build.VERSION.SDK_INT >= 33 -> this.getParcelable(key, clazz)
    else -> @Suppress("DEPRECATION") clazz.cast(this.getParcelable(key))
}