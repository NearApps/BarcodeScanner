package com.github.nearapps.barcodescanner.data.file

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream

class FileStream(private val context: Context) {
    fun export(uri: Uri, action: (OutputStream) -> Unit): Boolean {
        var successful = true
        try {
            context.contentResolver.openOutputStream(uri)?.let { outputStream ->
                action(outputStream)
                outputStream.flush()
                outputStream.close()
            }
        } catch (e: Exception) {
            successful = false
            e.printStackTrace()
        }
        return successful
    }

    fun import(uri: Uri, action: (InputStream) -> Unit): Boolean {
        var successful = true
        try {
            context.contentResolver.openInputStream(uri)?.let { outputStream ->
                action(outputStream)
                outputStream.close()
            }
        } catch (e: Exception) {
            successful = false
            e.printStackTrace()
        }
        return successful
    }
}