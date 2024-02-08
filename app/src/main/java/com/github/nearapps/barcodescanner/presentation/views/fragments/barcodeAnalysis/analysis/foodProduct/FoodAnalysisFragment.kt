package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.ApiAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.ingredients.FoodAnalysisRootIngredientsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.nutritionFacts.FoodAnalysisRootNutritionFactsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview.FoodAnalysisRootOverviewFragment
import com.github.nearapps.barcodescanner.presentation.views.viewPagerAdapters.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisFragment: ApiAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        super.start(analysis)

        // Permet de pré-charger les deux Fragments supplémentaires du ViewPager
        viewBinding.fragmentFoodAnalysisViewPager.offscreenPageLimit = 2

        configureFoodProductView(analysis)
    }

    private fun configureFoodProductView(barcodeAnalysis: FoodBarcodeAnalysis){

        val overviewFragment = FoodAnalysisRootOverviewFragment.newInstance(barcodeAnalysis)
        val ingredientsFragment = FoodAnalysisRootIngredientsFragment.newInstance(barcodeAnalysis)
        val nutritionFragment = FoodAnalysisRootNutritionFactsFragment.newInstance(barcodeAnalysis)
        val adapter = FragmentPagerAdapter(childFragmentManager, lifecycle, overviewFragment, ingredientsFragment, nutritionFragment)

        val overview: String = getString(R.string.overview_tab_label)
        val ingredients: String = getString(R.string.ingredients_label)
        val nutrition: String = getString(R.string.nutrition_facts_tab_label)
        configureViewPager(adapter, overview, ingredients, nutrition)
    }

    // ---- ViewPager Configuration ----
    private fun configureViewPager(adapter: FragmentStateAdapter, vararg textTab: String){

        val viewPager = viewBinding.fragmentFoodAnalysisViewPager
        val tabLayout = viewBinding.fragmentFoodAnalysisTabLayout

        viewPager.adapter=adapter

        if(textTab.isNotEmpty()) {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = textTab[position]
            }.attach()

            tabLayout.visibility = View.VISIBLE
        }else{
            tabLayout.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(foodBarcodeAnalysis: FoodBarcodeAnalysis) = FoodAnalysisFragment().apply {
            arguments = get<Bundle>().apply {
                putSerializable(BARCODE_ANALYSIS_KEY, foodBarcodeAnalysis)
            }
        }
    }
}