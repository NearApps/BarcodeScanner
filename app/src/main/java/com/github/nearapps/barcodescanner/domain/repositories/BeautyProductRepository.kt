package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface BeautyProductRepository {
    suspend fun getBeautyProduct(barcode: Barcode): FoodBarcodeAnalysis?
}