package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.api.OpenBeautyFactsService
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.repositories.BeautyProductRepository

class BeautyProductRepositoryImpl(private val service: OpenBeautyFactsService): BeautyProductRepository {
    override suspend fun getBeautyProduct(barcode: Barcode): FoodBarcodeAnalysis? {
        val beautyProductResponse = service.getOpenBeautyFactsData(barcode.contents)

        if(beautyProductResponse.status == 0 || beautyProductResponse.productResponse == null)
            return null

        return beautyProductResponse.toModel(barcode, RemoteAPI.OPEN_BEAUTY_FACTS)
    }
}