package com.github.nearapps.barcodescanner.domain.repositories

import android.net.Uri
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface FileStreamRepository {
    fun exportToCsv(barcodes: List<Barcode>, uri: Uri): Boolean
    fun exportToJson(barcodes: List<Barcode>, uri: Uri): Boolean
    fun importFromJson(uri: Uri): List<Barcode>?
}