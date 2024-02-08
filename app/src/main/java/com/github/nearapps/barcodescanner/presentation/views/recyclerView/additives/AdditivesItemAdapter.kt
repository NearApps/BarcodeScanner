package com.github.nearapps.barcodescanner.presentation.views.recyclerView.additives

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAdditivesBinding
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Additive

class AdditivesItemAdapter(
    private val showAdditiveInfoDialog: (additiveName: String, description: String) -> Unit,
    private val searchAdditiveOnTheWeb: (additiveId: String) -> Unit
): RecyclerView.Adapter<AdditivesItemHolder>() {

    private val additivesList = mutableListOf<Additive>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditivesItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemAdditivesBinding.inflate(layoutInflater, parent, false)

        return AdditivesItemHolder(viewBinding, showAdditiveInfoDialog, searchAdditiveOnTheWeb)
    }

    override fun getItemCount(): Int = additivesList.size

    override fun onBindViewHolder(holder: AdditivesItemHolder, position: Int) {
        holder.updateItem(additivesList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(additivesList: List<Additive>) {
        this.additivesList.clear()
        this.additivesList.addAll(additivesList)
        this.notifyDataSetChanged()
    }
}