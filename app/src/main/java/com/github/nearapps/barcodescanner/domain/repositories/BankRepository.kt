package com.github.nearapps.barcodescanner.domain.repositories

import androidx.lifecycle.LiveData
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank

/**
 * Repository permettant les interactions avec la BDD.
 */
interface BankRepository {

    fun getBankList(): LiveData<List<Bank>>

    suspend fun insertBank(bank: Bank): Long

    suspend fun deleteAllBank(): Int

    suspend fun deleteBanks(banks: List<Bank>): Int

    suspend fun deleteBank(bank: Bank): Int
}