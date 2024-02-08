package com.github.nearapps.barcodescanner.domain.repositories

import androidx.lifecycle.LiveData
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType

/**
 * Repository permettant les interactions avec la BDD.
 */
interface BarcodeRepository {

    fun getBarcodeList(): LiveData<List<Barcode>>

    fun getBarcodeByDate(date: Long): LiveData<Barcode?>

    suspend fun insertBarcode(barcode: Barcode): Long

    suspend fun insertBarcodes(barcodes: List<Barcode>)

    suspend fun update(date: Long, contents: String, barcodeType: BarcodeType, name: String): Int

    suspend fun updateType(date: Long, barcodeType: BarcodeType): Int

    suspend fun updateName(date: Long, name: String): Int

    suspend fun updateTypeAndName(date: Long, barcodeType: BarcodeType, name: String): Int

    suspend fun deleteAllBarcode(): Int

    suspend fun deleteBarcodes(barcodes: List<Barcode>): Int

    suspend fun deleteBarcode(barcode: Barcode): Int
}