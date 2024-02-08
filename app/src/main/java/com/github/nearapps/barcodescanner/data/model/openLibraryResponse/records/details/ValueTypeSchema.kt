package com.github.nearapps.barcodescanner.data.model.openLibraryResponse.records.details

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ValueTypeSchema(
    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("value")
    @Expose
    val value: String? = null
)