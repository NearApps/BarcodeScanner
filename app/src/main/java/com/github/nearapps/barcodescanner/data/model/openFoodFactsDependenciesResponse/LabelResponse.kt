package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class LabelResponse(
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("known")
    @Expose
    val known: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("products")
    @Expose
    val products: Int? = null,

    @SerializedName("sameAs")
    @Expose
    val sameAs: List<String?>? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null
)