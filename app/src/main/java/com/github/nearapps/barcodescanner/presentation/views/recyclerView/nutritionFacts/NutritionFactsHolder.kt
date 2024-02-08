package com.github.nearapps.barcodescanner.presentation.views.recyclerView.nutritionFacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.setBackground
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemNutritionFactsBinding
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutrient
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.NutritionFactsEnum

class NutritionFactsHolder(private val viewBinding: RecyclerViewItemNutritionFactsBinding): RecyclerView.ViewHolder(viewBinding.root) {

    private val context = itemView.context

    fun updateItem(nutrient: Nutrient, showServing: Boolean) {
        // Pour les sous-nutriments
        if(nutrient.entitled == NutritionFactsEnum.SATURATED_FAT ||
            nutrient.entitled == NutritionFactsEnum.SUGARS ||
            nutrient.entitled == NutritionFactsEnum.STARCH ||
            nutrient.entitled == NutritionFactsEnum.SODIUM){

            viewBinding.root.setBackground(R.attr.colorRow)
            val leftPadding = context.resources.getDimension(R.dimen.standard_margin).toInt()
            viewBinding.recyclerViewItemNutritionFactsEntitledTextView.setPadding(leftPadding,
                viewBinding.recyclerViewItemNutritionFactsEntitledTextView.paddingTop,
                viewBinding.recyclerViewItemNutritionFactsEntitledTextView.paddingRight,
                viewBinding.recyclerViewItemNutritionFactsEntitledTextView.paddingBottom)
        }

        viewBinding.recyclerViewItemNutritionFactsEntitledTextView.text = context.getString(nutrient.entitled.stringResource)

        viewBinding.recyclerViewItemNutritionFacts100gValueTextView.text = nutrient.values.getValue100gString()

        if (showServing)
            viewBinding.recyclerViewItemNutritionFactsServingValueTextView.text = nutrient.values.getValueServingString()
        else
            viewBinding.recyclerViewItemNutritionFactsServingValueTextView.visibility = View.GONE
    }
}