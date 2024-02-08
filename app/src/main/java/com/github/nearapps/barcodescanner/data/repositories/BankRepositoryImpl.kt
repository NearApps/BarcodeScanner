package com.github.nearapps.barcodescanner.data.repositories

import androidx.lifecycle.LiveData
import com.github.nearapps.barcodescanner.data.database.BankDao
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.github.nearapps.barcodescanner.domain.repositories.BankRepository

class BankRepositoryImpl(private val bankDao: BankDao): BankRepository {

    override fun getBankList(): LiveData<List<Bank>> = bankDao.getBankList()

    override suspend fun insertBank(bank: Bank): Long = bankDao.insert(bank)

    override suspend fun deleteAllBank(): Int = bankDao.deleteAll()

    override suspend fun deleteBanks(banks: List<Bank>): Int = bankDao.deleteBanks(banks)

    override suspend fun deleteBank(bank: Bank): Int = bankDao.delete(bank)
}