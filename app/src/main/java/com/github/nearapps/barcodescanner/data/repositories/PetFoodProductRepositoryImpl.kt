package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.api.OpenPetFoodFactsService
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.repositories.PetFoodProductRepository

class PetFoodProductRepositoryImpl(private val service: OpenPetFoodFactsService): PetFoodProductRepository {
    override suspend fun getPetFoodProduct(barcode: Barcode): FoodBarcodeAnalysis? {
        val petFoodProductResponse =  service.getOpenPetFoodFactsData(barcode.contents)

        if(petFoodProductResponse.status == 0 || petFoodProductResponse.productResponse == null)
            return null

        return petFoodProductResponse.toModel(barcode, RemoteAPI.OPEN_PET_FOOD_FACTS)
    }
}