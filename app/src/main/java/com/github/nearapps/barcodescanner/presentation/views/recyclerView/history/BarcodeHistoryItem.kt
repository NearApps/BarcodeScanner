package com.github.nearapps.barcodescanner.presentation.views.recyclerView.history

import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

data class BarcodeHistoryItem(val barcode: Barcode, var isSelected: Boolean = false)