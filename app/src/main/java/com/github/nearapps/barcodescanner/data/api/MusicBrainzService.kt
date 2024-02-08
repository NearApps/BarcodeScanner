package com.github.nearapps.barcodescanner.data.api

import com.github.nearapps.barcodescanner.data.model.musicBrainzResponse.musicAlbumInfo.MusicAlbumInfoResponse
import com.github.nearapps.barcodescanner.data.model.musicBrainzResponse.musicAlbumTracks.MusicAlbumTracksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicBrainzService {
    @GET("release/")
    suspend fun getAlbumInfoFromBarcode(@Query("query") barcode: String, @Query("fmt") fmt: String = "json"): MusicAlbumInfoResponse?

    @GET("release/{DISC_ID}")
    suspend fun getAlbumTracks(@Path("DISC_ID") discId: String, @Query("inc") inc: String = "recordings", @Query("fmt") fmt: String = "json"): MusicAlbumTracksResponse?
}