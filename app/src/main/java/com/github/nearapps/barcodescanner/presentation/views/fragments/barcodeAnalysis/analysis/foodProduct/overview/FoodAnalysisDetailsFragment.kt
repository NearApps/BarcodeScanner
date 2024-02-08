package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.convertToString
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisDetailsBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.dependencies.Country
import com.github.nearapps.barcodescanner.presentation.viewmodel.ExternalFileViewModel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisDetailsFragment: BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private val viewModel: ExternalFileViewModel by activityViewModel()

    private var _binding: FragmentFoodAnalysisDetailsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        viewBinding.root.fixAnimateLayoutChangesInNestedScroll()
        configureCategories(analysis)
        configurePackaging(analysis)
        configureStores(analysis)

        observeCountries(analysis)
    }

    private fun configureCategories(foodProduct: FoodBarcodeAnalysis) = configureExpandableCardViewFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisDetailsCategoriesFrameLayout,
        title = getString(R.string.categories_label),
        contents = foodProduct.categories
    )

    private fun configurePackaging(foodProduct: FoodBarcodeAnalysis) = configureExpandableCardViewFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisDetailsPackagingFrameLayout,
        title = getString(R.string.packaging_label),
        contents = foodProduct.packaging
    )

    private fun configureStores(foodProduct: FoodBarcodeAnalysis) = configureExpandableCardViewFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisDetailsStoresFrameLayout,
        title = getString(R.string.stores_label),
        contents = foodProduct.stores
    )

    private fun configureOriginsCountries(countries: String?) = configureExpandableCardViewFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisDetailsOriginsCountriesFrameLayout,
        title = getString(R.string.origins_label),
        contents = countries
    )

    private fun configureSalesCountries(countries: String?) = configureExpandableCardViewFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisDetailsSalesCountriesFrameLayout,
        title = getString(R.string.countries_label),
        contents = countries
    )

    private fun observeCountries(foodProduct: FoodBarcodeAnalysis){

        val countriesTags = foodProduct.countriesTagList

        if(!countriesTags.isNullOrEmpty()) {

            viewModel.obtainCountriesList(countriesTags).observe(viewLifecycleOwner) {

                val salesCountriesStr: String?
                val originsCountriesStr: String?
                if (it.isNotEmpty()) {
                    salesCountriesStr =
                        obtainCountriesString(foodProduct.salesCountriesTagsList, it)
                    originsCountriesStr =
                        obtainCountriesString(foodProduct.originsCountriesTagsList, it)
                } else {
                    salesCountriesStr = foodProduct.salesCountriesTagsList?.convertToString()
                    originsCountriesStr = foodProduct.originsCountriesTagsList?.convertToString()
                }

                configureSalesCountries(salesCountriesStr)
                configureOriginsCountries(originsCountriesStr)
            }
        }else{
            viewBinding.fragmentFoodAnalysisDetailsOriginsCountriesFrameLayout.visibility = View.GONE
            viewBinding.fragmentFoodAnalysisDetailsSalesCountriesFrameLayout.visibility = View.GONE
        }
    }

    private fun obtainCountriesString(countriesTagsList: List<String>?, countriesList: List<Country>): String?{
        return if (!countriesTagsList.isNullOrEmpty()) {
            val countries = mutableListOf<String>()
            for (country in countriesList) {
                if(countriesTagsList.contains(country.tag)){
                    countries.add(country.name)
                }
            }

            countries.sort()
            countries.convertToString()
        } else null
    }
}