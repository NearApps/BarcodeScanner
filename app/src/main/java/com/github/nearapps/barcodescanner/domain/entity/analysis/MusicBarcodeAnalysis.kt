package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.AlbumTrack

@Keep
class MusicBarcodeAnalysis(
    barcode: Barcode,
    source: RemoteAPI,
    val id: String?,
    val artists: List<String>?,
    val album: String?,
    val date: String?,
    val trackCount: Int?,
    val coverUrl: String?,
    val albumTracks: List<AlbumTrack>?
): BarcodeAnalysis(barcode, source)