package com.github.nearapps.barcodescanner.domain.entity.product.musicProduct

import com.github.nearapps.barcodescanner.data.model.musicBrainzResponse.musicAlbumInfo.MusicAlbumInfoResponse
import com.github.nearapps.barcodescanner.data.model.musicBrainzResponse.musicAlbumInfo.ReleaseSchema

fun obtainId(response: MusicAlbumInfoResponse): String? {
    val releases: List<ReleaseSchema>? = response.releases
    return if(!releases.isNullOrEmpty()) releases[0].id else null
}

fun obtainAlbum(response: MusicAlbumInfoResponse): String? {
    val releases: List<ReleaseSchema>? = response.releases
    return if(!releases.isNullOrEmpty()) releases[0].album else null
}

fun obtainArtists(response: MusicAlbumInfoResponse): List<String>? {
    val releases: List<ReleaseSchema>? = response.releases
    return if(!releases.isNullOrEmpty()) {
        releases[0].artists?.mapNotNull { it.name }
    } else null
}

fun obtainDate(response: MusicAlbumInfoResponse): String? {
    val releases: List<ReleaseSchema>? = response.releases
    return if(!releases.isNullOrEmpty()) releases[0].date else null
}

fun obtainTrackCount(response: MusicAlbumInfoResponse): Int? {
    val releases: List<ReleaseSchema>? = response.releases
    return if(!releases.isNullOrEmpty()) releases[0].trackCount else null
}