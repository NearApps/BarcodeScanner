package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.EnValue
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.LanguageValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class AdditiveClassResponse(
    @SerializedName("name")
    @Expose
    val name: LanguageValue? = null,

    @SerializedName("description")
    @Expose
    val description: LanguageValue? = null,

    @SerializedName("wikidata")
    @Expose
    val wikidata: EnValue? = null
)