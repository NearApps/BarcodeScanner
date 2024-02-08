package com.github.nearapps.barcodescanner.data.repositories

import com.github.nearapps.barcodescanner.data.api.CoverArtArchiveService
import com.github.nearapps.barcodescanner.data.api.MusicBrainzService
import com.github.nearapps.barcodescanner.domain.entity.analysis.MusicBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.AlbumTrack
import com.github.nearapps.barcodescanner.domain.repositories.MusicProductRepository

class MusicProductRepositoryImpl(
    private val musicBrainzService: MusicBrainzService,
    private val coverArtService: CoverArtArchiveService
): MusicProductRepository {
    override suspend fun getMusicProduct(barcode: Barcode): MusicBarcodeAnalysis? {
        val musicAlbumInfoResponse = musicBrainzService.getAlbumInfoFromBarcode("barcode:${barcode.contents}")

        if(musicAlbumInfoResponse?.releases == null || musicAlbumInfoResponse.count == 0)
            return null

        val id: String? = musicAlbumInfoResponse.releases.firstOrNull()?.id
        val coverUrl: String? = id?.let { getCoverArtUrl(it) }
        val albumTracks: List<AlbumTrack>? = id?.let { getAlbumTracks(it) }

        return musicAlbumInfoResponse.toModel(barcode, RemoteAPI.MUSICBRAINZ, coverUrl, albumTracks)
    }

    private suspend fun getCoverArtUrl(discId: String): String? {
        return try {
            val response = coverArtService.getCoverArt(discId)
            (response?.images?.firstOrNull()?.thumbnails?.small ?: response?.images?.firstOrNull()?.imageUrl)?.replace("http://", "https://")
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun getAlbumTracks(discId: String): List<AlbumTrack>? {
        return try {
            val response = musicBrainzService.getAlbumTracks(discId)
            response?.toModel()
        } catch (e: Exception) {
            null
        }
    }
}