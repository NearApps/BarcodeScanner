package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

@Keep
class UnknownProductBarcodeAnalysis(
    barcode: Barcode,
    val apiError: RemoteAPIError,
    val message: String? = null,
    source: RemoteAPI = RemoteAPI.NONE
): BarcodeAnalysis(barcode, source)