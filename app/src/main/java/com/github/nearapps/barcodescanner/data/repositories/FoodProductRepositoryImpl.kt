package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.api.OpenFoodFactsService
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.repositories.FoodProductRepository

class FoodProductRepositoryImpl(private val service: OpenFoodFactsService): FoodProductRepository {
    override suspend fun getFoodProduct(barcode: Barcode): FoodBarcodeAnalysis? {
        val foodProductResponse = service.getOpenFoodFactsData(barcode.contents)

        if(foodProductResponse.status == 0 || foodProductResponse.productResponse == null)
            return null

        return foodProductResponse.toModel(barcode, RemoteAPI.OPEN_FOOD_FACTS)
    }
}