package com.github.nearapps.barcodescanner.domain.entity.dependencies

data class Additive(val tag: String,
                    val name: String,
                    val additiveClassList: List<AdditiveClass>,
                    val overexposureRiskRate: OverexposureRiskRate){
    val additiveId: String = tag.removePrefix("en:").trim()
}