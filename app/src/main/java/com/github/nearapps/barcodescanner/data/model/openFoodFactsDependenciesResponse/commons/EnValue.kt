package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class EnValue(
    @SerializedName("en")
    @Expose
    val value: String? = null
)