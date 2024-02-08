package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import com.github.nearapps.barcodescanner.R

enum class NutritionFactsEnum(val stringResource: Int) {
    ENERGY_KJ(R.string.off_energy_kj_label),
    ENERGY_KCAL(R.string.off_energy_kcal_label),
    FAT(R.string.off_fat_label),
    SATURATED_FAT(R.string.off_saturated_fat_label),
    CARBOHYDRATES(R.string.off_carbohydrates_label),
    SUGARS(R.string.off_sugars_label),
    STARCH(R.string.off_starch_label),
    FIBER(R.string.off_fiber_label),
    PROTEINS(R.string.off_proteins_label),
    SALT(R.string.off_salt_label),
    SODIUM(R.string.off_sodium_label)
}