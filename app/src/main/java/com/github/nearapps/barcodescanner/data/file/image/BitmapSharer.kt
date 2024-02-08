package com.github.nearapps.barcodescanner.data.file.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Configure tous les pré-requis permettant le partage d'une image.
 */
class BitmapSharer(private val context: Context) {

    companion object {
        private const val AUTHORITY = "com.github.nearapps.barcodescanner.fileprovider"
    }

    fun share(bitmap: Bitmap): Uri? {
        val file = configureFile()
        val successful = writeBitmap(file, bitmap)
        return if (successful) FileProvider.getUriForFile(context, AUTHORITY, file) else null
    }

    /**
     * Configure un fichier dans le répertoire cache
     */
    private fun configureFile(): File {
        val imagesFolder = File(context.cacheDir, "images")
        imagesFolder.mkdirs()
        return File(imagesFolder, "image.png")
    }

    /**
     * Enregistre le Bitmap dans le File (cache)
     */
    private fun writeBitmap(file: File, bitmap: Bitmap): Boolean {
        var successful = false
        try {
            val outputStream: OutputStream = FileOutputStream(file)
            successful = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return successful
    }
}