package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.EcoScore
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.NovaGroup
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutrient
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutriscore
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.NutritionFactsEnum
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.PalmOilStatus
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VeganStatus
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VegetarianStatus
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VeggieIngredientAnalysis

@Keep
class FoodBarcodeAnalysis(
    barcode: Barcode,
    source: RemoteAPI,
    val name: String?,
    val brands: String?,
    val quantity: String?,
    val imageFrontUrl: String?,
    val labels: String?,
    val labelsTagList: List<String>?,
    val categories: String?,
    val packaging: String?,
    val stores: String?,
    val salesCountriesTagsList: List<String>?,
    val originsCountriesTagsList: List<String>?,
    val nutriscore: Nutriscore,
    val novaGroup: NovaGroup,
    val ecoScore: EcoScore,
    val ingredients: String?,
    val tracesTagsList: List<String>?,
    val allergensTagsList: List<String>?,
    val additivesTagsList: List<String>?,
    val veggieIngredientList: List<VeggieIngredientAnalysis>?,
    val veganStatus: VeganStatus,
    val vegetarianStatus: VegetarianStatus,
    val palmOilStatus: PalmOilStatus,
    val servingQuantity: Double?,
    val unit: String,
    val nutrientsList: List<Nutrient>
): BarcodeAnalysis(barcode, source) {

    val contains100gValues: Boolean = nutrientsList.any { it.values.value100g != null }

    val containsServingValues: Boolean = nutrientsList.any { it.values.valueServing != null }

    /**
     * Vérifie si on possède un Nutrient de type FAT ou SATURATED_FAT ou SUGARS ou SALT
     */
    val containsNutrientLevel: Boolean = nutrientsList.any {
        it.entitled == NutritionFactsEnum.FAT ||
        it.entitled == NutritionFactsEnum.SATURATED_FAT ||
        it.entitled == NutritionFactsEnum.SUGARS ||
        it.entitled == NutritionFactsEnum.SALT
    }

    // On met les tags des allergens et des traces ensemble car les 2 sont trouvables dans le même fichier (allergens.json)
    val allergensAndTracesTagList: List<String>? = when {
        allergensTagsList != null && tracesTagsList != null -> allergensTagsList + tracesTagsList
        allergensTagsList != null -> allergensTagsList
        tracesTagsList != null -> tracesTagsList
        else -> null
    }?.distinct()?.toList()

    val countriesTagList: List<String>? = when {
        salesCountriesTagsList != null && originsCountriesTagsList != null -> salesCountriesTagsList + originsCountriesTagsList
        salesCountriesTagsList != null -> salesCountriesTagsList
        originsCountriesTagsList != null -> originsCountriesTagsList
        else -> null
    }?.distinct()?.toList()
}