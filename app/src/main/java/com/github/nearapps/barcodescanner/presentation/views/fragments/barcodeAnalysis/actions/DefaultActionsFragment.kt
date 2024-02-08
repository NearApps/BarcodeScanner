package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem

class DefaultActionsFragment: AbstractActionsFragment() {
    override fun configureActions(barcode: Barcode): Array<ActionItem> {
        return configureDefaultActions(barcode)
    }
}