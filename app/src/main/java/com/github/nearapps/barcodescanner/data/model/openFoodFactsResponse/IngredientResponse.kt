package com.github.nearapps.barcodescanner.data.model.openFoodFactsResponse

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class IngredientResponse(
    @SerializedName("rank")
    @Expose
    val rank: Int? = null,

    @SerializedName("id")
    @Expose
    val id: String? = null,
    
    @SerializedName("text")
    @Expose
    val text: String? = null,
    
    @SerializedName("vegan")
    @Expose
    val vegan: String? = null,
    
    @SerializedName("vegetarian")
    @Expose
    val vegetarian: String? = null
)