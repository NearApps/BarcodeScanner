package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.details

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Key(
    @SerializedName("key")
    @Expose
    val key: String? = null
)