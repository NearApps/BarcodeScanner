package com.github.nearapps.barcodescanner.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.github.nearapps.barcodescanner.domain.usecases.DatabaseBankUseCase
import kotlinx.coroutines.launch

class DatabaseBankViewModel(private val databaseBankUseCase: DatabaseBankUseCase): ViewModel() {

    val bankList: LiveData<List<Bank>> = databaseBankUseCase.bankList

    fun insertBank(bank: Bank) = viewModelScope.launch {
        databaseBankUseCase.insertBank(bank)
    }

    fun deleteBank(bank: Bank) = viewModelScope.launch {
        databaseBankUseCase.deleteBank(bank)
    }

    fun deleteBanks(banks: List<Bank>) = viewModelScope.launch {
        databaseBankUseCase.deleteBanks(banks)
    }

    fun deleteAll() = viewModelScope.launch {
        databaseBankUseCase.deleteAll()
    }
}