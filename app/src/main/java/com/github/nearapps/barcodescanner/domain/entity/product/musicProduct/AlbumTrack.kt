package com.github.nearapps.barcodescanner.domain.entity.product.musicProduct

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class AlbumTrack(val title: String?,
                      val length: Long?,
                      val position: Int?): Serializable