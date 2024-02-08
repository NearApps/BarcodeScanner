package com.github.nearapps.barcodescanner.domain.repositories

import android.graphics.Bitmap
import android.net.Uri

interface ImageExportRepository {
    fun exportToPng(bitmap: Bitmap, uri: Uri): Boolean
    fun exportToJpg(bitmap: Bitmap, uri: Uri): Boolean
    fun exportToSvg(svg: String, uri: Uri): Boolean
    fun shareBitmap(bitmap: Bitmap): Uri?
}