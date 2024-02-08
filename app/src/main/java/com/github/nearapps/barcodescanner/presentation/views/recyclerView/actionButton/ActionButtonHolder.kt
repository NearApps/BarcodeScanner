package com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton

import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemActionButtonBinding

class ActionButtonHolder(private val viewBinding: RecyclerViewItemActionButtonBinding)
    : RecyclerView.ViewHolder(viewBinding.root) {

    fun updateItem(item: ActionItem) {
        viewBinding.root.setOnClickListener {
            item.listener.onItemClick(it)
        }
        viewBinding.templateActionButtonIcon.setImageResource(item.imageRes)
        viewBinding.templateActionButtonText.setText(item.textRes)
    }
}