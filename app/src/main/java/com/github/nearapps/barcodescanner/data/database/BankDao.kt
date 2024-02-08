package com.github.nearapps.barcodescanner.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank

@Dao
interface BankDao {

    @Query("SELECT * FROM Bank ORDER BY name")
    fun getBankList(): LiveData<List<Bank>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bank: Bank): Long

    @Query("DELETE FROM Bank")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun deleteBanks(banks: List<Bank>): Int

    @Delete
    suspend fun delete(bank: Bank): Int
}