package com.github.nearapps.barcodescanner.data.api

import com.github.nearapps.barcodescanner.data.model.openFoodFactsResponse.OpenFoodFactsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenBeautyFactsService {

    @GET("{productCode}")
    suspend fun getOpenBeautyFactsData(@Path("productCode") productCode: String): OpenFoodFactsResponse
}