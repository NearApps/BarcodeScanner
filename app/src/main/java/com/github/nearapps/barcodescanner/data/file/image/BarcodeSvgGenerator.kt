package com.github.nearapps.barcodescanner.data.file.image

import com.github.nearapps.barcodescanner.common.extensions.toColorAlpha
import com.github.nearapps.barcodescanner.common.extensions.toColorHex
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

/**
 * Génère l'image d'un code-barres à partir d'un texte.
 */
class BarcodeSvgGenerator(multiFormatWriter: MultiFormatWriter): BarcodeImageGenerator<String>(multiFormatWriter) {

    override fun createImageBarcode(
        properties: BarcodeImageGeneratorProperties,
        matrix: BitMatrix
    ): String {
        val builder = StringBuilder()
        buildSvgBegin(builder, totalWidth = properties.width, totalHeight = properties.height)
        buildSvgImageContent(builder, properties = properties, matrix = matrix)
        if(!properties.is2DBarcode)
            buildSvgTextContent(builder, properties = properties)
        buildSvgEnd(builder)
        return builder.toString()
    }

    // ---- SVG build ----

    private fun buildSvgBegin(builder: StringBuilder, totalWidth: Int, totalHeight: Int) {
        builder.append("<svg width=\"$totalWidth\" height=\"$totalHeight\" viewBox=\"0 0 $totalWidth $totalHeight\" xmlns=\"http://www.w3.org/2000/svg\">\n")
    }

    private fun buildSvgEnd(builder: StringBuilder) {
        builder.append("</svg>\n")
    }

    private fun buildSvgImageContent(builder: StringBuilder, properties: BarcodeImageGeneratorProperties, matrix: BitMatrix) {

        // ---- Background ----
        val backgroundFillColor = properties.backgroundColor.toColorHex()
        val backgroundAlpha: Float = properties.backgroundColor.toColorAlpha()
        builder.append("<rect x=\"0\" y=\"0\" width=\"${properties.width}\" height=\"${properties.height}\" style=\"fill:$backgroundFillColor;fill-opacity:$backgroundAlpha\"/>\n")

        // ---- Foreground ----
        val bitWidth: Float = properties.widthF / matrix.width.toFloat()
        val bitHeight: Float = (properties.heightF-properties.contentsHeight) / matrix.height.toFloat()
        val cornerRadius = bitWidth / 2f * properties.cornerRadius
        val foregroundFillColor = properties.frontColor.toColorHex()
        val foregroundAlpha: Float = properties.frontColor.toColorAlpha()

        for (y in 0 until matrix.height) {
            for (x in 0 until matrix.width) {
                if (matrix.get(x, y)) {
                    val posX = x * bitWidth
                    val posY = y * bitHeight
                    builder.append("<rect x=\"$posX\" y=\"$posY\" width=\"$bitWidth\" height=\"$bitHeight\" rx=\"$cornerRadius\" ry=\"$cornerRadius\" style=\"fill:$foregroundFillColor;fill-opacity:$foregroundAlpha\"/>\n")
                }
            }
        }
    }

    private fun buildSvgTextContent(builder: StringBuilder, properties: BarcodeImageGeneratorProperties) {
        val content = properties.contents
        val textSize = properties.contentsHeight
        val posX = properties.width / 2f
        val posY = properties.height - (properties.contentsHeight/10f)
        val fillColor = properties.frontColor.toColorHex()
        val fillAlpha: Float = properties.frontColor.toColorAlpha()

        builder.append("<text x=\"$posX\" y=\"$posY\" text-anchor=\"middle\" font-size=\"$textSize\" style=\"fill:$fillColor;fill-opacity:$fillAlpha\">$content</text>\n")
    }
}