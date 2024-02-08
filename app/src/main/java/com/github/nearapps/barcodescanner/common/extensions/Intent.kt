package com.github.nearapps.barcodescanner.common.extensions

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

fun <T : Serializable?> Intent.serializable(key: String?, clazz: Class<T>): T? {
    return this.extras?.serializable(key, clazz)
}

fun <T : Parcelable?> Intent.parcelable(key: String?, clazz: Class<T>): T? {
    return this.extras?.parcelable(key, clazz)
}