package com.github.nearapps.barcodescanner.presentation.views.recyclerView.veggie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.common.extensions.firstCharacterIntoCapital
import com.github.nearapps.barcodescanner.common.extensions.setTextColorFromAttrRes
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemVeggieBinding
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VeggieIngredientAnalysis

class VeggieItemHolder(private val viewBinding: RecyclerViewItemVeggieBinding): RecyclerView.ViewHolder(viewBinding.root) {

    private val context: Context = itemView.context

    fun update(veggieIngredientAnalysis: VeggieIngredientAnalysis){
        viewBinding.recyclerViewItemVeggieIngredientTextView.text = (veggieIngredientAnalysis.ingredientName ?: "?").trim().firstCharacterIntoCapital()

        val veganStatus = veggieIngredientAnalysis.veganStatus
        val vegetarianStatus = veggieIngredientAnalysis.vegetarianStatus

        viewBinding.recyclerViewItemVeggieVeganTextView.text = context.getText(veganStatus.stringResource)
        viewBinding.recyclerViewItemVeggieVeganTextView.setTextColorFromAttrRes(veganStatus.colorResource)
        viewBinding.recyclerViewItemVeggieVegetarianTextView.text = context.getText(vegetarianStatus.stringResource)
        viewBinding.recyclerViewItemVeggieVegetarianTextView.setTextColorFromAttrRes(vegetarianStatus.colorResource)
    }
}