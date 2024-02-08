package com.github.nearapps.barcodescanner.domain.library.camera

import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import com.github.nearapps.barcodescanner.common.extensions.toByteArray
import com.github.nearapps.barcodescanner.presentation.customView.ScanOverlay
import kotlin.math.roundToInt

class CameraXBarcodeAnalyzer(
    barcodeDetector: BarcodeDetector
) : AbstractCameraXBarcodeAnalyzer(barcodeDetector) {

    override fun analyze(image: ImageProxy) {
        val plane = image.planes[0]
        val imageData = plane.buffer.toByteArray()

        val size = image.width.coerceAtMost(image.height) * ScanOverlay.RATIO

        val left = (image.width - size) / 2f
        val top = (image.height - size) / 2f

        analyse(
            yuvData = imageData,
            dataWidth = plane.rowStride,
            dataHeight = image.height,
            left = left.roundToInt(),
            top = top.roundToInt(),
            width = size.roundToInt(),
            height = size.roundToInt()
        )

        image.close()
    }
}