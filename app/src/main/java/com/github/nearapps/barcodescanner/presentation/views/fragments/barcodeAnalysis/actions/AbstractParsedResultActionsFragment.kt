package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.ParsedResult
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

abstract class AbstractParsedResultActionsFragment: AbstractActionsFragment() {

    override fun configureActions(barcode: Barcode): Array<ActionItem> {
        return configureActions(
            barcode = barcode,
            parsedResult = get {
                parametersOf(barcode.contents, barcode.getBarcodeFormat())
            }
        )
    }

    abstract fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem>
}