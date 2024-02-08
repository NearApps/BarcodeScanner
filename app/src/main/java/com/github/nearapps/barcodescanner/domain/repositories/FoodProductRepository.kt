package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface FoodProductRepository {
    suspend fun getFoodProduct(barcode: Barcode): FoodBarcodeAnalysis?
}