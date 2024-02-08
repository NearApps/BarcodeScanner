package com.github.nearapps.barcodescanner.domain.entity.analysis

import androidx.annotation.Keep
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

@Keep
class BookBarcodeAnalysis(
    barcode: Barcode,
    source: RemoteAPI,
    val url: String?,
    val title: String?,
    val subtitle: String?,
    val originalTitle: String?,
    val authors: List<String>?,
    val coverUrl: String?,
    val description: String?,
    val publishDate: String?,
    val numberPages: Int?,
    val contributions: List<String>?,
    val publishers: List<String>?,
    val categories: List<String>?
): BarcodeAnalysis(barcode, source)