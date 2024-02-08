package com.github.nearapps.barcodescanner.data.api

import com.github.nearapps.barcodescanner.data.model.openLibraryResponse.OpenLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryService {

    @GET("/api/volumes/brief/json/id:information;isbn:{isbn}")
    suspend fun getOpenLibraryData(@Path("isbn") isbn: String): OpenLibraryResponse
}