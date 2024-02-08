package com.github.nearapps.barcodescanner.presentation.views.recyclerView.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.presentation.customView.CustomItemTouchHelperCallback

interface BarcodeHistoryItemTouchHelperListener: CustomItemTouchHelperCallback.ItemTouchHelperListener {

    override fun getForegroundView(viewHolder: RecyclerView.ViewHolder?): View? {
        return if (viewHolder is BarcodeHistoryItemHolder) viewHolder.getForegroundLayout() else null
    }
}