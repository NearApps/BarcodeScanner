package com.github.nearapps.barcodescanner.domain.entity.dependencies

import androidx.annotation.AttrRes
import com.github.nearapps.barcodescanner.R

enum class OverexposureRiskRate(val id: String, @AttrRes val colorResource: Int, val stringResource: Int) {
    LOW("en:no", R.attr.colorPositive, R.string.off_overexposure_risk_low_label),
    MODERATE("en:moderate", R.attr.colorMedium, R.string.off_overexposure_risk_moderate_label),
    HIGH("en:high",R.attr.colorNegative, R.string.off_overexposure_risk_high_label),
    NONE("", R.attr.colorUnknown, R.string.off_overexposure_risk_none_label)
}