package com.github.nearapps.barcodescanner.data.repositories

import android.graphics.Bitmap
import com.github.nearapps.barcodescanner.data.file.image.BarcodeBitmapGenerator
import com.github.nearapps.barcodescanner.data.file.image.BarcodeSvgGenerator
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.github.nearapps.barcodescanner.domain.repositories.ImageGeneratorRepository

class ImageGeneratorRepositoryImpl(
    private val bitmapGenerator: BarcodeBitmapGenerator,
    private val svgGenerator: BarcodeSvgGenerator
): ImageGeneratorRepository {

    override fun createBitmap(properties: BarcodeImageGeneratorProperties): Bitmap? {
        return bitmapGenerator.create(properties)
    }

    override fun createSvg(properties: BarcodeImageGeneratorProperties): String? {
        return svgGenerator.create(properties)
    }
}