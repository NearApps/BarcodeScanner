package com.github.nearapps.barcodescanner.data.file.image

import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

/**
 * Génère l'image d'un code-barres à partir d'un texte.
 */
abstract class BarcodeImageGenerator<T>(private val multiFormatWriter: MultiFormatWriter) {

    fun create(properties: BarcodeImageGeneratorProperties): T? {
        return try {
            val bitMatrix = encodeBarcodeImage(properties.contents, properties.format, properties.hints)
            createImageBarcode(properties, bitMatrix)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun encodeBarcodeImage(
        text: String,
        barcodeFormat: BarcodeFormat,
        hints: Map<EncodeHintType, Any>
    ): BitMatrix {
        return multiFormatWriter.encode(text, barcodeFormat, 0, 0, hints)
    }

    protected abstract fun createImageBarcode(properties: BarcodeImageGeneratorProperties, matrix: BitMatrix): T
}