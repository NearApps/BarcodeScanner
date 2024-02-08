package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.analysis.BookBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface BookProductRepository {
    suspend fun getBookProduct(barcode: Barcode): BookBarcodeAnalysis?
}