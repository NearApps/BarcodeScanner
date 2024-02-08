package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.intent.createAddEmailIntent
import com.github.nearapps.barcodescanner.presentation.intent.createSendEmailIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.EmailAddressParsedResult
import com.google.zxing.client.result.ParsedResult

class EmailActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is EmailAddressParsedResult -> configureEmailActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureEmailActions(barcode: Barcode, parsedResult: EmailAddressParsedResult) = arrayOf(
        ActionItem(R.string.action_send_mail_label, R.drawable.baseline_mail_24, sendEmail(parsedResult)),
        ActionItem(R.string.action_add_to_contacts, R.drawable.baseline_contacts_24, addEmailAddressToContact(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun addEmailAddressToContact(parsedResult: EmailAddressParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createAddEmailIntent(parsedResult)
            mStartActivity(intent)
        }
    }

    private fun sendEmail(parsedResult: EmailAddressParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createSendEmailIntent(requireContext(), parsedResult)
            mStartActivity(intent)
        }
    }
}