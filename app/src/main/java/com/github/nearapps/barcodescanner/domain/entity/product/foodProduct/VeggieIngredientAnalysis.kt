package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import java.io.Serializable

data class VeggieIngredientAnalysis(val ingredientName: String?,
                                    val veganStatus: VeganStatus,
                                    val vegetarianStatus: VegetarianStatus): Serializable