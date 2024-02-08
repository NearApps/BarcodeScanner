package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.nutritionFacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisNutritionFactsTableBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.nutritionFacts.NutritionFactsAdapter

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisNutritionFactsTableFragment : BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisNutritionFactsTableBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisNutritionFactsTableBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        configureHeaderRowTable(analysis)
        configureRecyclerView(analysis)
    }

    private fun configureHeaderRowTable(foodProduct: FoodBarcodeAnalysis){
        // Entitled
        viewBinding.fragmentFoodAnalysisNutritionFactsTableEntitled100TextView.text =
            getString(R.string.off_per_100_label, foodProduct.unit)

        if(!foodProduct.containsServingValues){
            viewBinding.fragmentFoodAnalysisNutritionFactsTableEntitledServingTextView.visibility = View.GONE
        }
        else {
            viewBinding.fragmentFoodAnalysisNutritionFactsTableEntitledServingTextView.text =
                if(foodProduct.servingQuantity==null)
                    getString(R.string.off_per_serving_no_quantity_label)
                else
                    getString(R.string.off_per_serving_label, foodProduct.servingQuantity.toString(), foodProduct.unit)
        }

    }

    private fun configureRecyclerView(foodProduct: FoodBarcodeAnalysis){
        viewBinding.fragmentFoodAnalysisNutritionFactsTableRecyclerView.adapter = NutritionFactsAdapter(foodProduct.nutrientsList, foodProduct.containsServingValues)
        viewBinding.fragmentFoodAnalysisNutritionFactsTableRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.fragmentFoodAnalysisNutritionFactsTableRecyclerView.suppressLayout(true)
    }
}