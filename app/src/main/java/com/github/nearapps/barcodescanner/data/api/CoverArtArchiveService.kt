package com.github.nearapps.barcodescanner.data.api

import com.github.nearapps.barcodescanner.data.model.covertArtArchiveResponse.CoverArtArchiveResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoverArtArchiveService {
    @GET("release/{MBID}")
    suspend fun getCoverArt(@Path("MBID") mbid: String): CoverArtArchiveResponse?
}