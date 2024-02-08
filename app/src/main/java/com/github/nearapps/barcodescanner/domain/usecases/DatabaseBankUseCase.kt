package com.github.nearapps.barcodescanner.domain.usecases

import androidx.lifecycle.LiveData
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.github.nearapps.barcodescanner.domain.repositories.BankRepository

class DatabaseBankUseCase(private val bankRepository: BankRepository) {

    val bankList: LiveData<List<Bank>> = bankRepository.getBankList()

    suspend fun insertBank(bank: Bank): Long = bankRepository.insertBank(bank)

    suspend fun deleteBank(bank: Bank) = bankRepository.deleteBank(bank)

    suspend fun deleteBanks(banks: List<Bank>) = bankRepository.deleteBanks(banks)

    suspend fun deleteAll(): Int = bankRepository.deleteAllBank()
}