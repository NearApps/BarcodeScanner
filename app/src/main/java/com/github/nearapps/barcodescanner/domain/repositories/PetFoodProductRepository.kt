package com.github.nearapps.barcodescanner.domain.repositories

import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

interface PetFoodProductRepository {
    suspend fun getPetFoodProduct(barcode: Barcode): FoodBarcodeAnalysis?
}