package com.github.nearapps.barcodescanner.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

@Dao
interface BarcodeDao {

    @Query("SELECT * FROM Barcode ORDER BY scan_date DESC")
    fun getBarcodeList(): LiveData<List<Barcode>>

    @Query("SELECT * FROM Barcode WHERE scan_date = :date LIMIT 1")
    fun getBarcodeByDate(date: Long): LiveData<Barcode?>

    @Insert
    suspend fun insert(barcode: Barcode): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcodes: List<Barcode>)

    @Query("UPDATE Barcode SET contents = :contents, type = :type, name = :name WHERE scan_date = :date")
    suspend fun update(date: Long, contents: String, type: String, name: String): Int

    @Query("UPDATE Barcode SET type = :type WHERE scan_date = :date")
    suspend fun updateType(date: Long, type: String): Int

    @Query("UPDATE Barcode SET name = :name WHERE scan_date = :date")
    suspend fun updateName(date: Long, name: String): Int

    @Query("UPDATE Barcode SET type = :type, name = :name WHERE scan_date = :date")
    suspend fun updateTypeAndName(date: Long, type: String, name: String): Int

    @Query("DELETE FROM Barcode")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteBarcodes(barcodes: List<Barcode>): Int

    @Delete
    suspend fun delete(barcode: Barcode): Int
}