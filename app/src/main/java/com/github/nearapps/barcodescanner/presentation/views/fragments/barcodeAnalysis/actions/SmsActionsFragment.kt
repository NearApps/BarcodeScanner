package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.intent.createAddSmsNumberIntent
import com.github.nearapps.barcodescanner.presentation.intent.createCallSmsNumberIntent
import com.github.nearapps.barcodescanner.presentation.intent.createSendSmsToSmsNumberIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.SMSParsedResult

class SmsActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is SMSParsedResult -> configureSmsActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureSmsActions(barcode: Barcode, parsedResult: SMSParsedResult) = arrayOf(
        ActionItem(R.string.action_send_sms_label, R.drawable.baseline_textsms_24, sendSms(parsedResult)),
        ActionItem(R.string.action_call_phone_label, R.drawable.baseline_call_24, callSmsPhone(parsedResult)),
        ActionItem(R.string.action_add_to_contacts, R.drawable.baseline_contacts_24, addSmsPhoneNumberToContact(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun callSmsPhone(parsedResult: SMSParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createCallSmsNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }

    private fun sendSms(parsedResult: SMSParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createSendSmsToSmsNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }

    private fun addSmsPhoneNumberToContact(parsedResult: SMSParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createAddSmsNumberIntent(parsedResult)
            mStartActivity(intent)
        }
    }
}