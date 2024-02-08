package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import com.github.nearapps.barcodescanner.R

enum class EcoScore(val drawableResource: Int, val descriptionStringResource: Int) {
    A(R.drawable.ecoscore_a, R.string.eco_score_description_a),
    B(R.drawable.ecoscore_b, R.string.eco_score_description_b),
    C(R.drawable.ecoscore_c, R.string.eco_score_description_c),
    D(R.drawable.ecoscore_d, R.string.eco_score_description_d),
    E(R.drawable.ecoscore_e, R.string.eco_score_description_e),
    UNKNOWN(-1, R.string.eco_score_description_unknown)
}