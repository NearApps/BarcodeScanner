package com.github.nearapps.barcodescanner.data.model.openFoodFactsResponse

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.common.extensions.polishText
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class OpenFoodFactsResponse(
    @SerializedName("status")
    @Expose
    var status: Int = 0,

    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("product")
    @Expose
    var productResponse: FoodProductResponse? = null
) {

    fun toModel(barcode: Barcode, source: RemoteAPI): FoodBarcodeAnalysis = FoodBarcodeAnalysis(
        barcode = barcode,
        source = source,
        name = productResponse?.productName?.polishText(),
        brands = productResponse?.brands?.polishText()?.polishText(),
        quantity = productResponse?.quantity?.polishText(),
        imageFrontUrl = productResponse?.imageFrontUrl,
        categories = productResponse?.categories?.polishText(),
        labels = productResponse?.labels?.polishText(),
        labelsTagList = productResponse?.labelsTags,
        packaging = productResponse?.packaging?.polishText(),
        stores = productResponse?.stores?.polishText(),
        salesCountriesTagsList = productResponse?.countriesTags,
        originsCountriesTagsList = productResponse?.originsTags,
        nutriscore = getNutriscore(productResponse),
        novaGroup = getNovaGroup(productResponse),
        ecoScore = getEcoScore(productResponse),
        ingredients = productResponse?.ingredientsTextWithAllergens ?: productResponse?.ingredientsText ?: productResponse?.ingredientsTextWithAllergensFr ?: productResponse?.ingredientsTextFr,
        tracesTagsList = productResponse?.tracesTags,
        allergensTagsList = productResponse?.allergensTags,
        additivesTagsList = productResponse?.additivesTags,
        veggieIngredientList = getVeggieIngredientAnalysisList(productResponse),
        veganStatus = getVeganStatus(productResponse),
        vegetarianStatus = getVegetarianStatus(productResponse),
        palmOilStatus = getPalmOilStatus(productResponse),
        servingQuantity = productResponse?.servingQuantity,
        unit = if (productResponse?.nutritionScoreBeverage == 1) "ml" else "g",
        nutrientsList = createNutrientsList(productResponse)
    )
}