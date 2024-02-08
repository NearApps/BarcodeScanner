package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.presentation.intent.createAddAgendaIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.CalendarParsedResult
import com.google.zxing.client.result.ParsedResult

class AgendaActionsFragment: AbstractParsedResultActionsFragment() {
    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is CalendarParsedResult -> configureAgendaActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureAgendaActions(barcode: Barcode, parsedResult: CalendarParsedResult) = arrayOf(
        ActionItem(R.string.action_add_to_calendar, R.drawable.baseline_event_note_24, addToAgenda(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun addToAgenda(parsedResult: CalendarParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val intent = createAddAgendaIntent(parsedResult)
            mStartActivity(intent)
        }
    }
}