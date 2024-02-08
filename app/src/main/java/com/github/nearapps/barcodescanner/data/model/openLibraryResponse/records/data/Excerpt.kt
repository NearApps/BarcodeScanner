package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.data

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Excerpt(
    @SerializedName("comment")
    @Expose
    val comment: String? = null,

    @SerializedName("text")
    @Expose
    val text: String? = null
)