package com.github.nearapps.barcodescanner.presentation.views.recyclerView.veggie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemVeggieBinding
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VeggieIngredientAnalysis

class VeggieItemAdapter(private val veggieList: List<VeggieIngredientAnalysis>): RecyclerView.Adapter<VeggieItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeggieItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemVeggieBinding.inflate(inflater, parent, false)
        return VeggieItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = veggieList.size

    override fun onBindViewHolder(holder: VeggieItemHolder, position: Int) {
        holder.update(veggieList[position])
    }
}