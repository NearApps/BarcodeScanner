package com.github.nearapps.barcodescanner.domain.library

import android.app.Activity
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.github.nearapps.barcodescanner.R
import java.io.IOException

/**
 * Code adapté de : https://github.com/journeyapps/zxing-android-embedded/blob/master/zxing-android-embedded/src/com/google/zxing/client/android/BeepManager.java
 */
class BeepManager {

    companion object {
        private val TAG = BeepManager::class.java.simpleName
        private const val BEEP_VOLUME = 0.10f
    }

    fun playBeepSound(activity: Activity): MediaPlayer? {

        activity.volumeControlStream = AudioManager.STREAM_MUSIC
        val context: Context = activity.applicationContext

        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        mediaPlayer.setOnCompletionListener { mp: MediaPlayer ->
            mp.stop()
            mp.reset()
            mp.release()
        }
        mediaPlayer.setOnErrorListener { mp: MediaPlayer, what: Int, extra: Int ->
            Log.w(TAG, "Failed to beep $what, $extra")
            // possibly media player error, so release and recreate
            mp.stop()
            mp.reset()
            mp.release()
            true
        }
        return try {

            val file = context.resources.openRawResourceFd(R.raw.zxing_beep)
            try {
                mediaPlayer.setDataSource(file.fileDescriptor, file.startOffset, file.length)
            } finally {
                file.close()
            }
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer
        } catch (ioe: IOException) {
            Log.w(TAG, ioe)
            mediaPlayer.reset()
            mediaPlayer.release()
            null
        }
    }
}