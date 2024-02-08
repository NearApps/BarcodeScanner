package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.GeoParsedResult
import com.google.zxing.client.result.ParsedResult

class LocalizationActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is GeoParsedResult -> configureLocalizationActions(barcode)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureLocalizationActions(barcode: Barcode) = arrayOf(
        ActionItem(R.string.action_show_location, R.drawable.baseline_place_24, openUrl(barcode.contents))
    ) + configureDefaultActions(barcode)
}