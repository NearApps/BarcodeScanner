package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutBdd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding

class BddItemAdapter: RecyclerView.Adapter<BddItemHolder>() {

    private val bddArray: Array<Bdd> = Bdd.entries.toTypedArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BddItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val viewBinding = RecyclerViewItemAboutBinding.inflate(layoutInflater, parent, false)

        return BddItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = bddArray.size

    override fun onBindViewHolder(holder: BddItemHolder, position: Int) {
        holder.updateItem(bddArray[position])
    }
}