package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisRootOverviewBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.EcoScore
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.NovaGroup
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutriscore
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.about.BarcodeAboutOverviewFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.templates.ProductOverviewFragment
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisRootOverviewFragment : BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisRootOverviewBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisRootOverviewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        viewBinding.fragmentFoodAnalysisRootOverviewOuterView.fixAnimateLayoutChangesInNestedScroll()

        configureProductOverviewFragment(analysis)
        applyFragment(viewBinding.fragmentFoodAnalysisRootOverviewVeggieAnalysisFrameLayout.id, FoodAnalysisVeggieFragment::class, arguments)
        configureQuality(analysis)
        configureDetails(analysis)
        configureBarcodeAboutOverviewFragment()
    }

    // ---- Overview ----

    private fun configureProductOverviewFragment(foodProduct: FoodBarcodeAnalysis) {
        val fragment = ProductOverviewFragment.newInstance(
            imageUrl = foodProduct.imageFrontUrl,
            title = foodProduct.name ?: getString(R.string.bar_code_type_unknown_product),
            subtitle1 = foodProduct.brands,
            subtitle2 = foodProduct.quantity
        )
        applyFragment(viewBinding.fragmentFoodAnalysisRootOverviewOverviewFrameLayout.id, fragment)
    }

    // ---- Quality ----

    private fun configureQuality(foodProduct: FoodBarcodeAnalysis){
        val nutriscore: Nutriscore = foodProduct.nutriscore
        val novaGroup: NovaGroup = foodProduct.novaGroup
        val ecoScore: EcoScore = foodProduct.ecoScore

        if(nutriscore == Nutriscore.UNKNOWN && novaGroup == NovaGroup.UNKNOWN && ecoScore == EcoScore.UNKNOWN) {
            viewBinding.fragmentFoodAnalysisRootOverviewQualityEntitledTextView.visibility = View.GONE
        }else {
            configureQualityEntitled()

            if (nutriscore != Nutriscore.UNKNOWN) {
                configureNutriScoreFragment(nutriscore)
            } else {
                viewBinding.fragmentFoodAnalysisRootOverviewQualityNutriScoreFrameLayout.visibility = View.GONE
            }

            if (novaGroup != NovaGroup.UNKNOWN) {
                configureNovaGroupFragment(novaGroup)
            } else {
                viewBinding.fragmentFoodAnalysisRootOverviewQualityNovaGroupFrameLayout.visibility = View.GONE
            }

            if (ecoScore != EcoScore.UNKNOWN) {
                configureEcoScoreFragment(ecoScore)
            } else {
                viewBinding.fragmentFoodAnalysisRootOverviewQualityEcoScoreFrameLayout.visibility = View.GONE
            }
        }
    }

    private fun configureQualityEntitled() {
        viewBinding.fragmentFoodAnalysisRootOverviewQualityEntitledTextView.visibility = View.VISIBLE
    }

    private fun configureNutriScoreFragment(nutriscore: Nutriscore) = configureQualityFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisRootOverviewQualityNutriScoreFrameLayout,
        title = getString(R.string.nutriscore_entitled_label),
        subtitle = getString(nutriscore.descriptionStringResource),
        description = getString(R.string.nutriscore_description),
        drawableRes = nutriscore.drawableResource
    )

    private fun configureNovaGroupFragment(noveGroup: NovaGroup) = configureQualityFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisRootOverviewQualityNovaGroupFrameLayout,
        title = getString(R.string.nova_group_entitled_label),
        subtitle = getString(noveGroup.descriptionStringResource),
        description = getString(R.string.nova_group_description),
        drawableRes = noveGroup.drawableResource
    )

    private fun configureEcoScoreFragment(ecoScore: EcoScore) = configureQualityFragment(
        frameLayout = viewBinding.fragmentFoodAnalysisRootOverviewQualityEcoScoreFrameLayout,
        title = getString(R.string.eco_score_entitled_label),
        subtitle = getString(ecoScore.descriptionStringResource),
        description = getString(R.string.eco_score_description),
        drawableRes = ecoScore.drawableResource
    )

    private fun configureQualityFragment(frameLayout: FrameLayout, title: String, subtitle: String, description: String, drawableRes: Int) {
        val fragment = FoodAnalysisQualityFragment.newInstance(
            drawableRes = drawableRes,
            title = title,
            subtitle = subtitle,
            description = description
        )
        applyFragment(frameLayout.id, fragment)
    }

    // ---- Details ----

    private fun configureDetails(foodProduct: FoodBarcodeAnalysis) {
        val categories: String? = foodProduct.categories
        val packaging: String? = foodProduct.packaging
        val stores: String? = foodProduct.stores
        val countries: List<String>? = foodProduct.countriesTagList
        val labels: String? = foodProduct.labels
        val labelsTags: List<String>? = foodProduct.labelsTagList

        if(categories.isNullOrBlank() && packaging.isNullOrBlank() && stores.isNullOrBlank() &&
            countries.isNullOrEmpty() && labels.isNullOrBlank() && labelsTags.isNullOrEmpty()) {
            viewBinding.fragmentFoodAnalysisRootOverviewDetailsEntitledTextView.visibility = View.GONE
        } else {
            viewBinding.fragmentFoodAnalysisRootOverviewDetailsEntitledTextView.visibility = View.VISIBLE

            // Labels Fragment
            if (!labels.isNullOrBlank() || !labelsTags.isNullOrEmpty())
                applyFragment(viewBinding.fragmentFoodAnalysisRootOverviewLabelsFrameLayout.id, FoodAnalysisLabelsFragment::class, arguments)

            // Details Fragment
            applyFragment(viewBinding.fragmentFoodAnalysisRootOverviewDetailsFrameLayout.id, FoodAnalysisDetailsFragment::class, arguments)
        }
    }

    // ---- Barcode ----

    private fun configureBarcodeAboutOverviewFragment() = applyFragment(
        containerViewId = viewBinding.fragmentFoodAnalysisRootOverviewBarcodeAboutOverviewFrameLayout.id,
        fragmentClass = BarcodeAboutOverviewFragment::class,
        args = arguments
    )

    companion object {
        fun newInstance(foodProduct: FoodBarcodeAnalysis) = FoodAnalysisRootOverviewFragment().apply {
            arguments = get<Bundle>().apply {
                putSerializable(BARCODE_ANALYSIS_KEY, foodProduct)
            }
        }
    }
}
