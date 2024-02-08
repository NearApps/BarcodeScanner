package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.URIParsedResult

class UrlActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is URIParsedResult -> configureUrlActions(barcode, parsedResult.uri)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureUrlActions(barcode: Barcode, uri: String) = arrayOf(
        ActionItem(R.string.action_open_link, R.drawable.baseline_open_in_browser_24, openUrl(uri)),
        ActionItem(R.string.share_text_label, R.drawable.baseline_share_24, shareTextContents(barcode.contents)),
        ActionItem(R.string.copy_barcode_label, R.drawable.baseline_content_copy_24, copyContents(barcode.contents)),
        ActionItem(R.string.action_modify_barcode, R.drawable.baseline_create_24, modifyBarcodeContents(barcode))
    )
}