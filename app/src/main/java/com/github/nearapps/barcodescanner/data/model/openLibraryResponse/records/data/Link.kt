package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Link(
    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null
)