package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisRootIngredientsBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisRootIngredientsFragment : BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisRootIngredientsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=FragmentFoodAnalysisRootIngredientsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {

        viewBinding.fragmentFoodAnalysisRootIngredientsOuterView.fixAnimateLayoutChangesInNestedScroll()
        configureIngredientsView(analysis)
        configureAdditivesView(analysis)
    }

    private fun configureIngredientsView(foodProduct: FoodBarcodeAnalysis) {
        if(foodProduct.ingredients != null && foodProduct.ingredients != "") {
            applyFragment(R.id.fragment_food_analysis_root_ingredients_ingredients_frame_layout, FoodAnalysisIngredientsFragment::class, arguments)
            viewBinding.fragmentFoodAnalysisRootIngredientsNoInformationTextView.visibility=View.GONE
        }
    }

    private fun configureAdditivesView(foodProduct: FoodBarcodeAnalysis) {
        if (foodProduct.additivesTagsList != null && foodProduct.additivesTagsList.isNotEmpty()) {
            applyFragment(R.id.fragment_food_analysis_root_ingredients_additives_frame_layout, FoodAnalysisAdditivesFragment::class, arguments)
            viewBinding.fragmentFoodAnalysisRootIngredientsNoInformationTextView.visibility=View.GONE
        }
    }

    companion object {
        fun newInstance(foodProduct: FoodBarcodeAnalysis) = FoodAnalysisRootIngredientsFragment().apply {
            arguments = get<Bundle>().apply {
                putSerializable(BARCODE_ANALYSIS_KEY, foodProduct)
            }
        }
    }
}
