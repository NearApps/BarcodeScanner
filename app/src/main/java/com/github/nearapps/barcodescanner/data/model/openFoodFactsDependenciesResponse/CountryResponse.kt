package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.EnValue
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.LanguageValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CountryResponse (

    @SerializedName("country_code_2")
    @Expose
    val countryCode2: EnValue? = null,

    @SerializedName("languages")
    @Expose
    val languages: EnValue? = null,

    @SerializedName("country_code_3")
    @Expose
    val countryCode3: EnValue? = null,

    @SerializedName("name")
    @Expose
    val name: LanguageValue? = null
)