package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.common.extensions.serializable
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.ActivityVeggieBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.VeggieIngredientAnalysis
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.veggie.VeggieItemAdapter

class VeggieActivity : BaseActivity() {

    private val viewBinding: ActivityVeggieBinding by lazy { ActivityVeggieBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureToolbar()

        val foodProduct: FoodBarcodeAnalysis? = intent.serializable(BARCODE_ANALYSIS_KEY, FoodBarcodeAnalysis::class.java)

        val veggieIngredientsList = foodProduct?.veggieIngredientList
        if(veggieIngredientsList.isNullOrEmpty()) {
            viewBinding.activityVeggieIngredientsListLayout.visibility = View.GONE
            viewBinding.activityVeggieNoIngredientsTextView.visibility = View.VISIBLE
        } else {
            configureRecyclerView(veggieIngredientsList)
            viewBinding.activityVeggieIngredientsListLayout.visibility = View.VISIBLE
            viewBinding.activityVeggieNoIngredientsTextView.visibility = View.GONE
        }

        setContentView(viewBinding.root)
    }

    private fun configureToolbar() {
        setSupportActionBar(viewBinding.activityVeggieToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)// On affiche l'icone "retour"
    }

    private fun configureRecyclerView(veggieIngredientList: List<VeggieIngredientAnalysis>) {
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        val adapter = VeggieItemAdapter(veggieIngredientList)
        viewBinding.activityVeggieRecyclerView.adapter = adapter
        viewBinding.activityVeggieRecyclerView.layoutManager = linearLayoutManager
        viewBinding.activityVeggieRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}