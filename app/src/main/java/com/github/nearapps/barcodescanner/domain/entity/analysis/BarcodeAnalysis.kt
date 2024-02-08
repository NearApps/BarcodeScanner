package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import java.io.Serializable

@Keep
open class BarcodeAnalysis(
    val barcode: Barcode,
    val source: RemoteAPI
): Serializable