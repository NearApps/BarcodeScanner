package com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.presentation.customView.CustomItemTouchHelperCallback

interface BankHistoryItemTouchHelperListener: CustomItemTouchHelperCallback.ItemTouchHelperListener {

    override fun getForegroundView(viewHolder: RecyclerView.ViewHolder?): View? {
        return if (viewHolder is BankHistoryItemHolder) viewHolder.getForegroundLayout() else null
    }
}