package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import androidx.annotation.AttrRes
import com.github.nearapps.barcodescanner.R

enum class VeganStatus(val stringResource: Int, @AttrRes val colorResource: Int, val drawableResource: Int) {
    VEGAN(R.string.is_vegan_label, R.attr.colorPositive, R.drawable.baseline_check_circle_outline_24),
    NO_VEGAN(R.string.no_vegan_label, R.attr.colorNegative, R.drawable.baseline_highlight_off_24),
    MAYBE_VEGAN(R.string.maybe_vegan_label, R.attr.colorMedium, R.drawable.baseline_help_outline_24),
    UNKNOWN_VEGAN(R.string.vegan_status_unknown_label, R.attr.colorUnknown, R.drawable.baseline_help_outline_24)
}