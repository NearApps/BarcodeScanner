package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.intent.createAddPhoneNumberIntent
import com.github.nearapps.barcodescanner.presentation.intent.createCallPhoneNumberIntent
import com.github.nearapps.barcodescanner.presentation.intent.createSendSmsToPhoneNumberIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.TelParsedResult

class PhoneActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is TelParsedResult -> configurePhoneActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configurePhoneActions(barcode: Barcode, parsedResult: TelParsedResult) = arrayOf(
        ActionItem(R.string.action_call_phone_label, R.drawable.baseline_call_24, callPhone(parsedResult)),
        ActionItem(R.string.action_send_sms_label, R.drawable.baseline_textsms_24, sendSmsToPhoneNumber(parsedResult)),
        ActionItem(R.string.action_add_to_contacts, R.drawable.baseline_contacts_24, addPhoneNumberToContact(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun callPhone(parsedResult: TelParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createCallPhoneNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }

    private fun sendSmsToPhoneNumber(parsedResult: TelParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createSendSmsToPhoneNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }

    private fun addPhoneNumberToContact(parsedResult: TelParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createAddPhoneNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }
}