package com.github.nearapps.barcodescanner.data.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.github.nearapps.barcodescanner.data.file.FileStream
import com.github.nearapps.barcodescanner.data.file.image.BitmapSharer
import com.github.nearapps.barcodescanner.domain.repositories.ImageExportRepository

class ImageExportRepositoryImpl(
    private val fileStream: FileStream,
    private val sharer: BitmapSharer
): ImageExportRepository {

    override fun exportToPng(bitmap: Bitmap, uri: Uri): Boolean {
        return fileStream.export(uri) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    override fun exportToJpg(bitmap: Bitmap, uri: Uri): Boolean {
        return fileStream.export(uri) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    override fun exportToSvg(svg: String, uri: Uri): Boolean {
        return fileStream.export(uri) {
            it.write(svg.toByteArray())
        }
    }

    override fun shareBitmap(bitmap: Bitmap): Uri? {
        return sharer.share(bitmap)
    }
}