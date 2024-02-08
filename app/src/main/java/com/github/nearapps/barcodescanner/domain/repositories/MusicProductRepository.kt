package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.analysis.MusicBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface MusicProductRepository {
    suspend fun getMusicProduct(barcode: Barcode): MusicBarcodeAnalysis?
}