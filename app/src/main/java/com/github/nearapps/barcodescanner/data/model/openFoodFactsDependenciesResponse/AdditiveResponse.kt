package com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.EnValue
import com.github.nearapps.barcodescanner.data.model.openFoodFactsDependenciesResponse.commons.LanguageValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Cette classe est une valeur de la liste "additives.json" convertit en Kotlin. Le
 * fichier est récupéré via l'URL "https://world.openfoodfacts.org/data/taxonomies/additives.json".
 */
@Keep
data class AdditiveResponse(
    @SerializedName("vegan")
    @Expose
    val vegan: EnValue? = null,

    @SerializedName("vegetarian")
    @Expose
    val vegetarian: EnValue? = null,

    @SerializedName("efsa_evaluation")
    @Expose
    val efsaEvaluation: EnValue? = null,

    @SerializedName("additives_classes")
    @Expose
    val additivesClasses: EnValue? = null,

    @SerializedName("efsa_evaluation_overexposure_risk")
    @Expose
    val efsaEvaluationOverexposureRisk: EnValue? = null,

    @SerializedName("organic_eu")
    @Expose
    val organicEu: EnValue? = null,

    @SerializedName("e_number")
    @Expose
    val eNumber: EnValue? = null,

    @SerializedName("wikidata")
    @Expose
    val wikidata: EnValue? = null,

    @SerializedName("efsa_evaluation_date")
    @Expose
    val efsaEvaluationDate: EnValue? = null,

    @SerializedName("efsa_evaluation_url")
    @Expose
    val efsaEvaluationUrl: EnValue? = null,

    @SerializedName("name")
    @Expose
    val name: LanguageValue? = null,

    @SerializedName("efsa_evaluation_exposure_95th_greater_than_adi")
    @Expose
    val efsaEvaluationExposure95thGreaterThanAdi: EnValue? = null,

    @SerializedName("efsa_evaluation_exposure_mean_greater_than_adi")
    @Expose
    val efsaEvaluationExposureMeanGreaterThanAdi: EnValue? = null
) {
    var tag: String = ""
}