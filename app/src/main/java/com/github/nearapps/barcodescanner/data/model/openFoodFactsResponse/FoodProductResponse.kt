package com.github.nearapps.barcodescanner.data.model.openFoodFactsResponse

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class FoodProductResponse(
    @SerializedName("product_name")
    @Expose
    val productName: String? = null,

    @SerializedName("brands")
    @Expose
    val brands: String? = null,

    @SerializedName("quantity")
    @Expose
    val quantity: String? = null,

    @SerializedName("image_front_url")
    @Expose
    val imageFrontUrl: String? = null,

    @SerializedName("ingredients_analysis_tags")
    @Expose
    val ingredientsAnalysisTags: List<String>? = null,

    @SerializedName("nutrition_grades")
    @Expose
    val nutritionGrades: String? = null,

    @SerializedName("nova_group")
    @Expose
    val novaGroup: Int? = null,

    @SerializedName("ecoscore_grade")
    @Expose
    val ecoScoreGrade: String? = null,

    @SerializedName("categories")
    @Expose
    val categories: String? = null,

    @SerializedName("packaging")
    @Expose
    val packaging: String? = null,

    @SerializedName("stores")
    @Expose
    val stores: String? = null,

    @SerializedName("countries_tags")
    @Expose
    val countriesTags: List<String>? = null,

    @SerializedName("origins_tags")
    @Expose
    val originsTags: List<String>? = null,

    @SerializedName("ingredients_text_with_allergens")
    @Expose
    val ingredientsTextWithAllergens: String? = null,

    @SerializedName("ingredients_text")
    @Expose
    val ingredientsText: String? = null,

    @SerializedName("ingredients_text_fr")
    @Expose
    val ingredientsTextFr: String? = null,

    @SerializedName("ingredients_text_with_allergens_fr")
    @Expose
    val ingredientsTextWithAllergensFr: String? = null,

    @SerializedName("allergens_tags")
    @Expose
    val allergensTags: List<String>? = null,

    @SerializedName("traces_tags")
    @Expose
    val tracesTags: List<String>? = null,

    @SerializedName("additives_tags")
    @Expose
    val additivesTags: List<String>? = null,

    @SerializedName("ingredients")
    @Expose
    val ingredientsResponses: List<IngredientResponse>? = null,

    @SerializedName("nutriments")
    @Expose
    val nutrimentsResponse: NutrimentsResponse? = null,

    @SerializedName("nutrition_score_beverage")
    @Expose
    val nutritionScoreBeverage: Int? = null,

    @SerializedName("serving_quantity")
    @Expose
    val servingQuantity: Double? = null,

    @SerializedName("labels")
    @Expose
    val labels: String? = null,

    @SerializedName("labels_tags")
    @Expose
    val labelsTags: List<String>? = null
)