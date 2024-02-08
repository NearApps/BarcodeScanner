package com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemActionButtonBinding

class ActionButtonAdapter: RecyclerView.Adapter<ActionButtonHolder>() {

    private var items: List<ActionItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionButtonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemActionButtonBinding.inflate(layoutInflater, parent, false)
        return ActionButtonHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ActionButtonHolder, position: Int) {
        holder.updateItem(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(actionItemArray: Array<ActionItem>){
        items = actionItemArray.map { it }
        this.notifyDataSetChanged()
    }
}