package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import androidx.annotation.AttrRes
import com.github.nearapps.barcodescanner.R

enum class VegetarianStatus(val stringResource: Int, @AttrRes val colorResource: Int, val drawableResource: Int) {
    VEGETARIAN(R.string.is_vegetarian_label, R.attr.colorPositive, R.drawable.baseline_check_circle_outline_24),
    NO_VEGETARIAN(R.string.no_vegetarian_label, R.attr.colorNegative, R.drawable.baseline_highlight_off_24),
    MAYBE_VEGETARIAN(R.string.maybe_vegetarian_label, R.attr.colorMedium, R.drawable.baseline_help_outline_24),
    UNKNOWN_VEGETARIAN(R.string.vegetarian_status_unknown_label, R.attr.colorUnknown, R.drawable.baseline_help_outline_24)
}