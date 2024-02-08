package com.github.nearapps.barcodescanner.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

@Database(entities = [Barcode::class, Bank::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun barcodeDao(): BarcodeDao

    abstract fun bankDao(): BankDao
}