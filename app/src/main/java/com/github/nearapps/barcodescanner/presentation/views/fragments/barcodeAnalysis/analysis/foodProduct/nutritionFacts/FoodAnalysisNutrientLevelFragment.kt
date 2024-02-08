package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.nutritionFacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.common.extensions.serializable
import com.github.nearapps.barcodescanner.common.extensions.setImageColorFromAttrRes
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisNutrientLevelBinding
import com.github.nearapps.barcodescanner.domain.entity.product.foodProduct.Nutrient
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisNutrientLevelFragment : Fragment() {

    private var nutrient: Nutrient? = null

    private var _binding: FragmentFoodAnalysisNutrientLevelBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nutrient = it.serializable(NUTRIENT_KEY, Nutrient::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisNutrientLevelBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSubView(nutrient)
    }

    private fun configureSubView(nutrient: Nutrient?) {

        if(nutrient != null) {
            // Entitled
            val entitled: String = getString(nutrient.entitled.stringResource)
            viewBinding.fragmentFoodAnalysisNutrientLevelTitleTextView.text = entitled

            // Subtitle
            val subtitle: String = getString(nutrient.getQuantityValue().stringResource)
            viewBinding.fragmentFoodAnalysisNutrientLevelSubtitleTextView.text = subtitle

            // Weight
            val weight: String = nutrient.values.getValue100gString()
            viewBinding.fragmentFoodAnalysisNutrientLevelQuantityTextView.text = weight

            // Indicator Image
            @AttrRes val colorRes: Int = nutrient.getQuantityValue().colorResource
            viewBinding.fragmentFoodAnalysisNutrientLevelIndicatorImageView.setImageColorFromAttrRes(colorRes)

            // GraphView
            configureGraphView(nutrient)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }

    private fun configureGraphView(nutrient: Nutrient) {

        val currentQuantity: Float? = nutrient.values.value100g?.toFloat()
        val lowQuantity: Float? = nutrient.getLowQuantity()
        val highQuantity: Float? = nutrient.getHighQuantity()

        if(currentQuantity!=null && lowQuantity!=null && highQuantity!=null) {

            viewBinding.fragmentFoodAnalysisNutrientLevelHorizontalGraphView.setValues(
                guidePosition = currentQuantity,
                low = lowQuantity,
                high = highQuantity
            )
        } else {
            viewBinding.fragmentFoodAnalysisNutrientLevelHorizontalGraphView.visibility = View.GONE
        }
    }

    companion object {
        private const val NUTRIENT_KEY = "nutrientKey"

        @JvmStatic
        fun newInstance(nutrient: Nutrient) =
            FoodAnalysisNutrientLevelFragment().apply {
                arguments = get<Bundle>().apply {
                    putSerializable(NUTRIENT_KEY, nutrient)
                }
            }
    }
}