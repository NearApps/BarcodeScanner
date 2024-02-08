package com.github.nearapps.barcodescanner.data.file.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.github.nearapps.barcodescanner.common.extensions.drawRectangle
import com.github.nearapps.barcodescanner.common.extensions.drawRoundRectangle
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

/**
 * Génère l'image d'un code-barres à partir d'un texte.
 */
class BarcodeBitmapGenerator(multiFormatWriter: MultiFormatWriter): BarcodeImageGenerator<Bitmap>(multiFormatWriter) {

    override fun createImageBarcode(
        properties: BarcodeImageGeneratorProperties,
        matrix: BitMatrix
    ): Bitmap {
        val bitmap: Bitmap =
            Bitmap.createBitmap(properties.width, properties.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        createBackground(canvas, paint, properties)
        if(!properties.is2DBarcode)
            createTextContentImage(canvas, paint, properties)
        createBarcodeImage(canvas, paint, matrix, properties)

        return bitmap
    }

    private fun createBackground(
        canvas: Canvas,
        paint: Paint,
        properties: BarcodeImageGeneratorProperties
    ) {
        paint.apply {
            this.color = properties.backgroundColor
            this.isAntiAlias = true
        }

        canvas.drawRectangle(
            left = 0f,
            top = properties.heightF,
            right = properties.widthF,
            bottom = 0f,
            paint = paint
        )
    }

    private fun createBarcodeImage(
        canvas: Canvas,
        paint: Paint,
        matrix: BitMatrix,
        properties: BarcodeImageGeneratorProperties
    ) {
        val unitW: Float = properties.widthF / matrix.width.toFloat()
        val unitH: Float = (properties.heightF-properties.contentsHeight) / matrix.height.toFloat()
        val cornerRadius = unitW / 2f * properties.cornerRadius
        paint.apply {
            this.color = properties.frontColor
            this.isAntiAlias = cornerRadius != 0f
        }

        for (x in 0 until matrix.width) {
            for (y in 0 until matrix.height) {
                if(matrix[x, y]) {
                    val left = x.toFloat() * unitW
                    val top = y.toFloat() * unitH
                    val right = left + unitW
                    val bottom = top + unitH

                    canvas.drawRoundRectangle(
                        left = left,
                        top = top,
                        right = right,
                        bottom = bottom,
                        rx = cornerRadius,
                        ry = cornerRadius,
                        paint = paint
                    )
                }
            }
        }
    }

    private fun createTextContentImage(
        canvas: Canvas,
        paint: Paint,
        properties: BarcodeImageGeneratorProperties
    ) {
        paint.apply {
            this.color = properties.frontColor
            this.isAntiAlias = true
            this.textSize = properties.contentsHeight
            this.textAlign = Paint.Align.CENTER
        }

        canvas.drawText(
            properties.contents,
            properties.width/2f,
            properties.height - (properties.contentsHeight/10f),
            paint.apply { color = properties.frontColor }
        )

        paint.reset()
    }
}