package com.github.nearapps.barcodescanner.presentation.views.recyclerView.nutritionFacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemNutritionFactsBinding
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutrient

class NutritionFactsAdapter(private val nutrientsList: List<Nutrient>, private val showServing: Boolean): RecyclerView.Adapter<NutritionFactsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionFactsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemNutritionFactsBinding.inflate(layoutInflater, parent, false)

        return NutritionFactsHolder(viewBinding)
    }

    override fun getItemCount(): Int = nutrientsList.size

    override fun onBindViewHolder(holder: NutritionFactsHolder, position: Int) {
        holder.updateItem(nutrientsList[position], showServing)
    }
}