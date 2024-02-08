package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.commons

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Author(
    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("key")
    @Expose
    val key: String? = null
)