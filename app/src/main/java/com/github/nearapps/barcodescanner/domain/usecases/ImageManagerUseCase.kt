package com.github.nearapps.barcodescanner.domain.usecases

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.github.nearapps.barcodescanner.domain.library.BarcodeImageGeneratorProperties
import com.github.nearapps.barcodescanner.domain.repositories.ImageExportRepository
import com.github.nearapps.barcodescanner.domain.repositories.ImageGeneratorRepository
import com.github.nearapps.barcodescanner.domain.resources.Resource
import kotlinx.coroutines.Dispatchers

class ImageManagerUseCase(
    private val imageGeneratorRepository: ImageGeneratorRepository,
    private val imageExportRepository: ImageExportRepository
) {

    val bitmapObserver = MutableLiveData<Resource<Bitmap?>>()

    suspend fun createBitmap(properties: BarcodeImageGeneratorProperties) {
        bitmapObserver.postValue(Resource.loading())
        val bitmap = imageGeneratorRepository.createBitmap(properties)
        bitmapObserver.postValue(Resource.success(bitmap))
    }

    fun createSvg(properties: BarcodeImageGeneratorProperties): LiveData<String?> = liveData(Dispatchers.IO) {
        try {
            val svg = imageGeneratorRepository.createSvg(properties)
            emit(svg)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }
    }

    fun exportToPng(bitmap: Bitmap?, uri: Uri): LiveData<Resource<Boolean>> {
        return export {
            bitmap?.let {
                imageExportRepository.exportToPng(bitmap, uri)
            } ?: run {
                false
            }
        }
    }

    fun exportToJpg(bitmap: Bitmap?, uri: Uri): LiveData<Resource<Boolean>> {
        return export {
            bitmap?.let {
                imageExportRepository.exportToJpg(it, uri)
            } ?: run {
                false
            }
        }
    }

    fun exportToSvg(svg: String?, uri: Uri): LiveData<Resource<Boolean>> {
        return export {
            svg?.let {
                imageExportRepository.exportToSvg(it, uri)
            } ?: run {
                false
            }
        }
    }

    fun shareBitmap(bitmap: Bitmap?): LiveData<Resource<Uri?>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val uri: Uri? = bitmap?.let {
                imageExportRepository.shareBitmap(it)
            } ?: run {
                null
            }
            emit(Resource.success(uri))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.failure(e, null))
        }
    }

    private fun export(
        export: () -> Boolean
    ): LiveData<Resource<Boolean>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val success = export()
            emit(Resource.success(success))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.failure(e, false))
        }
    }
}