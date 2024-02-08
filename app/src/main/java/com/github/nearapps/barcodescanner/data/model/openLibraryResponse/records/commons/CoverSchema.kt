package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CoverSchema(
    @SerializedName("small")
    @Expose
    val small: String? = null,

    @SerializedName("large")
    @Expose
    val large: String? = null,

    @SerializedName("medium")
    @Expose
    val medium: String? = null
)