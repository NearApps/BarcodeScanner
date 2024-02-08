package com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory

import com.github.nearapps.barcodescanner.domain.entity.bank.Bank

data class BankHistoryItem(val bank: Bank, var isSelected: Boolean = false)