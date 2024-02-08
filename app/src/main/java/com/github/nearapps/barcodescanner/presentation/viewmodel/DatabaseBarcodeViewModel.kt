package com.github.nearapps.barcodescanner.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.nearapps.barcodescanner.domain.entity.FileFormat
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.domain.resources.Resource
import com.github.nearapps.barcodescanner.domain.usecases.DatabaseBarcodeUseCase
import kotlinx.coroutines.launch

class DatabaseBarcodeViewModel(private val databaseBarcodeUseCase: DatabaseBarcodeUseCase): ViewModel() {

    val barcodeList: LiveData<List<Barcode>> = databaseBarcodeUseCase.barcodeList

    fun getBarcodeByDate(date: Long): LiveData<Barcode?> = databaseBarcodeUseCase.getBarcodeByDate(date)

    fun insertBarcode(barcode: Barcode) = viewModelScope.launch {
        databaseBarcodeUseCase.insertBarcode(barcode)
    }

    fun update(date: Long, contents: String, barcodeType: BarcodeType, name: String) = viewModelScope.launch {
        databaseBarcodeUseCase.update(date, contents, barcodeType, name)
    }

    fun updateType(date: Long, barcodeType: BarcodeType) = viewModelScope.launch {
        databaseBarcodeUseCase.updateType(date, barcodeType)
    }

    fun updateTypeAndName(date: Long, barcodeType: BarcodeType, name: String) = viewModelScope.launch {
        databaseBarcodeUseCase.updateTypeAndName(date, barcodeType, name)
    }

    fun deleteBarcode(barcode: Barcode) = viewModelScope.launch {
        databaseBarcodeUseCase.deleteBarcode(barcode)
    }

    fun deleteBarcodes(barcodes: List<Barcode>) = viewModelScope.launch {
        databaseBarcodeUseCase.deleteBarcodes(barcodes)
    }

    fun deleteAll() = viewModelScope.launch {
        databaseBarcodeUseCase.deleteAll()
    }

    fun exportToFile(
        barcodes: List<Barcode>,
        format: FileFormat,
        uri: Uri
    ): LiveData<Resource<Boolean>> {
        return when(format) {
            FileFormat.CSV -> databaseBarcodeUseCase.exportToCsv(barcodes, uri)
            FileFormat.JSON -> databaseBarcodeUseCase.exportToJson(barcodes, uri)
        }
    }

    fun importFile(uri: Uri): LiveData<Resource<Boolean>> {
        return databaseBarcodeUseCase.importFromJson(uri)
    }
}