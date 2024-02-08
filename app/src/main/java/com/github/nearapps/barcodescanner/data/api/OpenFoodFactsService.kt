package com.github.nearapps.barcodescanner.data.api

import com.github.nearapps.barcodescanner.data.model.openFoodFactsResponse.OpenFoodFactsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodFactsService {

    @GET("{productCode}")
    suspend fun getOpenFoodFactsData(@Path("productCode") productCode: String): OpenFoodFactsResponse
}