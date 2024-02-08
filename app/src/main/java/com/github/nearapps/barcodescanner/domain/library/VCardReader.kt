package com.github.nearapps.barcodescanner.domain.library

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import java.io.InputStream

/**
 * Recupère un VCard au format String à partir de son Uri.
 */
class VCardReader(private val context: Context) {

    fun readVCardFromContactUri(uri: Uri): String? {

        var vCardText: String? = null

        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        if (cursor?.moveToFirst() == true) {

            val columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)
            val lookupKey: String = cursor.getString(columnIndex)

            val uriWithAppendPath: Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey)

            try {
                val fd = context.contentResolver.openAssetFileDescriptor(uriWithAppendPath, "r")

                if (fd != null) {
                    val inputStream: InputStream = fd.createInputStream()
                    vCardText = inputStream.readBytes().toString(Charsets.UTF_8)
                    fd.close()
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        cursor?.close()

        return vCardText
    }
}