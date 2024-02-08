package com.github.nearapps.barcodescanner.data.model.musicBrainzResponse.musicAlbumInfo

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.analysis.MusicBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.AlbumTrack
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.obtainAlbum
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.obtainArtists
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.obtainDate
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.obtainId
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.obtainTrackCount
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class MusicAlbumInfoResponse(
    @SerializedName("count")
    @Expose
    val count: Int? = null,

    @SerializedName("releases")
    @Expose
    val releases: List<ReleaseSchema>? = null
) {
    fun toModel(barcode: Barcode, source: RemoteAPI, coverUrl: String?, albumTracks: List<AlbumTrack>?): MusicBarcodeAnalysis = MusicBarcodeAnalysis(
        barcode = barcode,
        source = source,
        id = obtainId(this),
        artists = obtainArtists(this),
        album = obtainAlbum(this),
        date = obtainDate(this),
        trackCount = obtainTrackCount(this),
        coverUrl = coverUrl,
        albumTracks = albumTracks
    )
}