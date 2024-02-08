package com.github.nearapps.barcodescanner.data.file

import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class UrlConnector {

    private var input: BufferedInputStream? = null

    fun openConnection(urlStr: String): BufferedInputStream? {

        try {
            val url = URL(urlStr)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            input = BufferedInputStream(url.openStream())

        } catch (e: Exception){
            input=null
            e.printStackTrace()
        }

        return input
    }

    fun closeConnection(){
        input?.close()
    }
}