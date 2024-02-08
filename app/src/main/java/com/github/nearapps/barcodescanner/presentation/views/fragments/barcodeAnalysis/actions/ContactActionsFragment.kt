package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.intent.createAddContactIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.AddressBookParsedResult
import com.google.zxing.client.result.ParsedResult

class ContactActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is AddressBookParsedResult -> configureContactActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureContactActions(barcode: Barcode, parsedResult: AddressBookParsedResult) = arrayOf(
        ActionItem(R.string.action_add_to_contacts, R.drawable.baseline_contacts_24, addToContact(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun addToContact(parsedResult: AddressBookParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createAddContactIntent(parsedResult)
            mStartActivity(intent)
        }
    }
}