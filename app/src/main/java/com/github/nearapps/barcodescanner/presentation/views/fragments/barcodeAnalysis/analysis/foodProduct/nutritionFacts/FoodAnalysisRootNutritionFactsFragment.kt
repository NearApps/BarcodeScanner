package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.nutritionFacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisRootNutritionFactsBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutrient
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.NutritionFactsEnum
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisRootNutritionFactsFragment : BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisRootNutritionFactsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisRootNutritionFactsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        viewBinding.fragmentFoodAnalysisRootNutritionFactsOuterView.fixAnimateLayoutChangesInNestedScroll()
        configureTable(analysis)
        configureNutrientLevel(analysis)
    }

    // ---- Table ----

    private fun configureTable(foodProduct: FoodBarcodeAnalysis){
        viewBinding.fragmentFoodAnalysisRootNutritionFactsTableEntitledLayout.visibility = View.GONE
        if(foodProduct.contains100gValues || foodProduct.containsServingValues) {
            configureTableEntitled()
            configureOffNutritionFactsTableFragment()
            viewBinding.fragmentFoodAnalysisRootNutritionFactsNoInformationTextView.visibility = View.GONE
        }
    }

    private fun configureTableEntitled(){
        viewBinding.fragmentFoodAnalysisRootNutritionFactsTableEntitledLayout.visibility = View.VISIBLE
    }

    private fun configureOffNutritionFactsTableFragment() = applyFragment(
        containerViewId = R.id.fragment_food_analysis_root_nutrition_facts_table_frame_layout,
        fragmentClass = FoodAnalysisNutritionFactsTableFragment::class,
        args = arguments
    )

    // ---- Nutrient Level ----


    private fun configureNutrientLevel(foodProduct: FoodBarcodeAnalysis){

        if(foodProduct.containsNutrientLevel){
            configureNutrientLevelEntitled()
            configureFoodProductNutrientLevelFragments(foodProduct.nutrientsList)
        }else{
            viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelEntitledLayout.visibility = View.GONE
        }
    }

    private fun configureFoodProductNutrientLevelFragments(nutrientsList: List<Nutrient>){

        nutrientsList.forEach {
            when (it.entitled) {
                NutritionFactsEnum.FAT -> configureFatFragment(it)
                NutritionFactsEnum.SATURATED_FAT -> configureSaturatedFatFragment(it)
                NutritionFactsEnum.SUGARS -> configureSugarsFragment(it)
                NutritionFactsEnum.SALT -> configureSaltFragment(it)
                else -> {}
            }
        }
    }

    private fun configureFatFragment(nutrient: Nutrient) = applyFragment(
        containerViewId = viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelFatLayout.id,
        fragment = FoodAnalysisNutrientLevelFragment.newInstance(nutrient)
    )

    private fun configureSaturatedFatFragment(nutrient: Nutrient) = applyFragment(
        containerViewId = viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelSaturatedFatLayout.id,
        fragment = FoodAnalysisNutrientLevelFragment.newInstance(nutrient)
    )

    private fun configureSugarsFragment(nutrient: Nutrient) = applyFragment(
        containerViewId = viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelSugarsLayout.id,
        fragment = FoodAnalysisNutrientLevelFragment.newInstance(nutrient)
    )

    private fun configureSaltFragment(nutrient: Nutrient) = applyFragment(
        containerViewId = viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelSaltLayout.id,
        fragment = FoodAnalysisNutrientLevelFragment.newInstance(nutrient)
    )

    private fun configureNutrientLevelEntitled(){
        viewBinding.fragmentFoodAnalysisRootNutritionFactsNutrientLevelEntitledLayout.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(foodProduct: FoodBarcodeAnalysis) = FoodAnalysisRootNutritionFactsFragment().apply {
            arguments = get<Bundle>().apply {
                putSerializable(BARCODE_ANALYSIS_KEY, foodProduct)
            }
        }
    }
}
