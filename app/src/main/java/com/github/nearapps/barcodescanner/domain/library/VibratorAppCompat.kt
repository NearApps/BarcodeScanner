package com.github.nearapps.barcodescanner.domain.library

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi

class VibratorAppCompat(private val context: Context) {

    companion object {
        private const val VIBRATE_DURATION = 500L
    }

    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            createVibratorManager().defaultVibrator
        } else {
            createVibrator()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun createVibratorManager(): VibratorManager {
        return context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    }

    @Suppress("DEPRECATION")
    private fun createVibrator(): Vibrator {
        return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun vibrate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            @Suppress("DEPRECATION")
            vibrator.vibrate(VIBRATE_DURATION)
        }
    }
}