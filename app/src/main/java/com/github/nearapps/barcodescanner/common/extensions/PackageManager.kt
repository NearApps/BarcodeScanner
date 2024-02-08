package com.github.nearapps.barcodescanner.common.extensions

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build

fun PackageManager.queryIntentActivitiesAppCompat(intent: Intent, i: Int): List<ResolveInfo> {
    return if (Build.VERSION.SDK_INT >= 33) {
        this.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(i.toLong()))
    } else {
        @Suppress("DEPRECATION")
        this.queryIntentActivities(intent, i)
    }
}