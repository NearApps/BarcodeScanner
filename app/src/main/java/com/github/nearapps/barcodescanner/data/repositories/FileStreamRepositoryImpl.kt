package com.github.nearapps.barcodescanner.data.repositories

import android.net.Uri
import com.github.nearapps.barcodescanner.data.file.FileStream
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.repositories.FileStreamRepository
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class FileStreamRepositoryImpl(private val fileStream: FileStream): FileStreamRepository {

    private val gson by lazy { Gson() }

    override fun exportToCsv(barcodes: List<Barcode>, uri: Uri): Boolean {
        return fileStream.export(uri) { outputStream ->
            val strBuilder = StringBuilder()
            strBuilder.append("Barcode,Format,Scan Date,Type,Error Correction Level,Name\n")
            barcodes.forEach { barcode ->
                val contents = "\"${barcode.contents}\""
                val formatName = "\"${barcode.formatName}\""
                val scanDate = "\"${barcode.scanDate}\""
                val type = "\"${barcode.type}\""
                val errorCorrectionLevel = "\"${barcode.errorCorrectionLevel}\""
                val name = "\"${barcode.name}\""
                strBuilder.append("$contents,$formatName,$scanDate,$type,$errorCorrectionLevel,$name\n")
            }
            outputStream.write(strBuilder.toString().toByteArray())
        }
    }

    override fun exportToJson(barcodes: List<Barcode>, uri: Uri): Boolean {
        return fileStream.export(uri) { outputStream ->
            outputStream.write(gson.toJson(barcodes).toByteArray())
        }
    }

    override fun importFromJson(uri: Uri): List<Barcode>? {
        var barcodes: List<Barcode>? = null
        val successful = fileStream.import(uri) {
            val reader = BufferedReader(InputStreamReader(it))
            val jsonString = reader.readText()

            barcodes = gson.fromJson(jsonString, Array<Barcode>::class.java).toList()

            reader.close()
        }

        return if(successful) barcodes else null
    }
}