package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.details

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class DetailsMain(
    @SerializedName("info_url")
    @Expose
    val infoUrl: String? = null,

    @SerializedName("bib_key")
    @Expose
    val bibKey: String? = null,
    
    @SerializedName("preview_url")
    @Expose
    val previewUrl: String? = null,
    
    @SerializedName("thumbnail_url")
    @Expose
    val thumbnailUrl: String? = null,
    
    @SerializedName("details")
    @Expose
    val details: Details? = null,
    
    @SerializedName("preview")
    @Expose
    val preview: String? = null
)