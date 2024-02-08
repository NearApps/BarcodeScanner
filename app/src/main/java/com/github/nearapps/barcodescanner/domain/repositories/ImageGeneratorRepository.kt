package com.github.nearapps.barcodescanner.domain.repositories

import android.graphics.Bitmap
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties

interface ImageGeneratorRepository {
    fun createBitmap(properties: BarcodeImageGeneratorProperties): Bitmap?
    fun createSvg(properties: BarcodeImageGeneratorProperties): String?
}