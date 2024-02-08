package com.github.nearapps.barcodescanner.domain.entity.product.foodProduct

import androidx.annotation.AttrRes
import com.github.nearapps.barcodescanner.R

enum class PalmOilStatus(val stringResource: Int, @AttrRes val colorResource: Int, val drawableResource: Int) {
    PALM_OIL_FREE(R.string.palm_oil_free_label, R.attr.colorPositive, R.drawable.baseline_check_circle_outline_24),
    PALM_OIL(R.string.contain_palm_oil_label, R.attr.colorNegative, R.drawable.baseline_highlight_off_24),
    MAYBE_PALM_OIL(R.string.may_contain_palm_oil_label, R.attr.colorMedium, R.drawable.baseline_help_outline_24),
    UNKNOWN_PALM_OIL(R.string.palm_oil_content_unknown_label, R.attr.colorUnknown, R.drawable.baseline_help_outline_24)
}