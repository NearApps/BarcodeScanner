package com.github.nearapps.barcodescanner.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.nearapps.barcodescanner.common.utils.DATABASE_NAME

fun createBarcodeDao(database: AppDatabase): BarcodeDao = database.barcodeDao()
fun createBankDao(database: AppDatabase): BankDao = database.bankDao()
fun createDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addMigrations(/*MIGRATION_1_2*/)
        .build()

/*private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Bank (name TEXT NOT NULL, bic TEXT NOT NULL, iban TEXT NOT NULL PRIMARY KEY)"
        )
    }
}*/
