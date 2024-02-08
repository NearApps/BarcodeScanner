package com.github.nearapps.barcodescanner.domain.library

import android.graphics.Bitmap
import android.util.Log
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.ReaderException
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer


/**
 * Recherche un code-barres dans une image.
 */
class BarcodeBitmapAnalyser {

    private val reader = MultiFormatReader()

    fun detectBarcodeFromBitmap(bitmap: Bitmap): Result? {

        val width = bitmap.width
        val height = bitmap.height
        val size = width * height

        val bitmapBuffer = IntArray(size)

        bitmap.getPixels(bitmapBuffer, 0, width, 0, 0, width, height)

        val source = RGBLuminanceSource(width, height, bitmapBuffer)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        //val hints = hashMapOf<DecodeHintType, Any>()
        //hints[DecodeHintType.TRY_HARDER] = true
        //hints[DecodeHintType.PURE_BARCODE] = true

        reader.reset()

        return try {
            reader.decode(binaryBitmap)
        } catch (e: NotFoundException) {
            val invertedSource = source.invert()
            val invertedBinaryBitmap = BinaryBitmap(HybridBinarizer(invertedSource))
            reader.reset()
            try {
                reader.decode(invertedBinaryBitmap)
            } catch (e: ReaderException) {
                Log.e("BitmapBarcodeAnalyser", "Barcode not found in Bitmap")
                null
            }
        }
    }
}