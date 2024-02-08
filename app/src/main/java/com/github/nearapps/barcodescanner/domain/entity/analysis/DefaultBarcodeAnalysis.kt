package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

@Keep
class DefaultBarcodeAnalysis(
    barcode: Barcode,
    source: RemoteAPI = RemoteAPI.NONE
): BarcodeAnalysis(barcode, source)