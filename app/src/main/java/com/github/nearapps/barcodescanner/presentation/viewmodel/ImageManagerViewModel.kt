package com.github.nearapps.barcodescanner.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.github.nearapps.barcodescanner.domain.resources.Resource
import com.github.nearapps.barcodescanner.domain.usecases.ImageManagerUseCase
import kotlinx.coroutines.launch

class ImageManagerViewModel(private val imageManagerUseCase: ImageManagerUseCase): ViewModel() {
    fun getBitmap(): LiveData<Resource<Bitmap?>> = imageManagerUseCase.bitmapObserver

    fun createBitmap(properties: BarcodeImageGeneratorProperties) = viewModelScope.launch {
        imageManagerUseCase.createBitmap(properties)
    }

    fun createSvg(properties: BarcodeImageGeneratorProperties): LiveData<String?> {
        return imageManagerUseCase.createSvg(properties)
    }

    fun exportAsPng(bitmap: Bitmap?, uri: Uri): LiveData<Resource<Boolean>> {
        return imageManagerUseCase.exportToPng(bitmap, uri)
    }

    fun exportAsJpg(bitmap: Bitmap?, uri: Uri): LiveData<Resource<Boolean>> {
        return imageManagerUseCase.exportToJpg(bitmap, uri)
    }

    fun exportAsSvg(properties: BarcodeImageGeneratorProperties, uri: Uri): LiveData<Resource<Boolean>> {
        return createSvg(properties).switchMap { svg: String? ->
            imageManagerUseCase.exportToSvg(svg, uri)
        }
    }

    fun shareBitmap(bitmap: Bitmap?): LiveData<Resource<Uri?>> {
        return imageManagerUseCase.shareBitmap(bitmap)
    }
}