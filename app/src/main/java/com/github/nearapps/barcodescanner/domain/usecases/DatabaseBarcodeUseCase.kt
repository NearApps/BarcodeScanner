package com.github.nearapps.barcodescanner.domain.usecases

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.domain.repositories.BarcodeRepository
import com.github.nearapps.barcodescanner.domain.repositories.FileStreamRepository
import com.github.nearapps.barcodescanner.domain.resources.Resource
import kotlinx.coroutines.Dispatchers

class DatabaseBarcodeUseCase(
    private val barcodeRepository: BarcodeRepository,
    private val fileStreamRepository: FileStreamRepository
) {

    val barcodeList: LiveData<List<Barcode>> = barcodeRepository.getBarcodeList()

    fun getBarcodeByDate(date: Long): LiveData<Barcode?> = barcodeRepository.getBarcodeByDate(date)

    suspend fun insertBarcode(barcode: Barcode): Long = barcodeRepository.insertBarcode(barcode)

    suspend fun insertBarcodes(barcodes: List<Barcode>) = barcodeRepository.insertBarcodes(barcodes)

    suspend fun update(date: Long, contents: String, barcodeType: BarcodeType, name: String): Int =
        barcodeRepository.update(date, contents, barcodeType, name)

    suspend fun updateType(date: Long, barcodeType: BarcodeType): Int =
        barcodeRepository.updateType(date, barcodeType)

    suspend fun updateTypeAndName(date: Long, barcodeType: BarcodeType, name: String): Int =
        barcodeRepository.updateTypeAndName(date, barcodeType, name)

    suspend fun deleteBarcode(barcode: Barcode) = barcodeRepository.deleteBarcode(barcode)

    suspend fun deleteBarcodes(barcodes: List<Barcode>) = barcodeRepository.deleteBarcodes(barcodes)

    suspend fun deleteAll(): Int = barcodeRepository.deleteAllBarcode()

    fun exportToCsv(
        barcodes: List<Barcode>,
        uri: Uri
    ): LiveData<Resource<Boolean>> {
        return doAction {
            fileStreamRepository.exportToCsv(barcodes, uri)
        }
    }

    fun exportToJson(
        barcodes: List<Barcode>,
        uri: Uri
    ): LiveData<Resource<Boolean>> {
        return doAction {
            fileStreamRepository.exportToJson(barcodes, uri)
        }
    }

    fun importFromJson(uri: Uri): LiveData<Resource<Boolean>> = liveData(Dispatchers.IO) {
        val barcodes: List<Barcode>? = fileStreamRepository.importFromJson(uri)

        if(barcodes.isNullOrEmpty()) {
            emit(Resource.success(false))
        } else {
            try {
                insertBarcodes(barcodes)
                emit(Resource.success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.failure(e, false))
            }
        }
    }

    private fun doAction(
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