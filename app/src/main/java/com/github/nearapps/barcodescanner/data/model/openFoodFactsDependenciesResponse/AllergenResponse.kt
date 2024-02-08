package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.EnValue
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.LanguageValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Cette classe est une valeur de la liste du fichier "allergens.json" convertit en Kotlin. Le
 * fichier est récupéré via l'URL "https://world.openfoodfacts.org/data/taxonomies/allergens.json".
 */
@Keep
data class AllergenResponse(
    @SerializedName("name")
    @Expose
    val name: LanguageValue? = null,

    @SerializedName("wikidata")
    @Expose
    val wikidata: EnValue? = null
)