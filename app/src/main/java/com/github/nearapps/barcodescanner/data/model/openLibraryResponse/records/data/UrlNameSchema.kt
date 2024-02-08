package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class UrlNameSchema(
    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null
)