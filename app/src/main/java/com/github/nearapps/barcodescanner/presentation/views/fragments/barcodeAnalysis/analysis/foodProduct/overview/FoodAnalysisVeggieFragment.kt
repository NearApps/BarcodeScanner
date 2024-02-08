package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.common.extensions.firstCharacterIntoCapital
import com.github.nearapps.barcodescanner.common.extensions.setChipBackgroundColorFromAttrRes
import com.github.nearapps.barcodescanner.common.extensions.setChipIconTintFromAttrRes
import com.github.nearapps.barcodescanner.common.extensions.setChipStrokeColorFromAttrRes
import com.github.nearapps.barcodescanner.common.extensions.setChipTextColorFromAttrRes
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisVeggieBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.views.activities.VeggieActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.google.android.material.chip.Chip

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisVeggieFragment: BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private var _binding: FragmentFoodAnalysisVeggieBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisVeggieBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        configureChipGroup(analysis)

        val onClickListener: View.OnClickListener = getVeggieOnClickListener(analysis)
        viewBinding.fragmentFoodAnalysisVeggieVeganChip.setOnClickListener(onClickListener)
        viewBinding.fragmentFoodAnalysisVeggieVegetarianChip.setOnClickListener(onClickListener)
    }

    private fun configureChipGroup(foodProduct: FoodBarcodeAnalysis){

        configureChipWithIngredientsAnalysis(
            chipView = viewBinding.fragmentFoodAnalysisVeggieVeganChip,
            colorResource = foodProduct.veganStatus.colorResource,
            drawableResource = foodProduct.veganStatus.drawableResource,
            stringResource = foodProduct.veganStatus.stringResource
        )

        configureChipWithIngredientsAnalysis(
            chipView = viewBinding.fragmentFoodAnalysisVeggieVegetarianChip,
            colorResource = foodProduct.vegetarianStatus.colorResource,
            drawableResource = foodProduct.vegetarianStatus.drawableResource,
            stringResource = foodProduct.vegetarianStatus.stringResource
        )

        configureChipWithIngredientsAnalysis(
            chipView = viewBinding.fragmentFoodAnalysisVeggiePalmOilChip,
            colorResource = foodProduct.palmOilStatus.colorResource,
            drawableResource = foodProduct.palmOilStatus.drawableResource,
            stringResource = foodProduct.palmOilStatus.stringResource
        )
    }

    private fun configureChipWithIngredientsAnalysis(chipView: Chip, @AttrRes colorResource: Int, drawableResource: Int, stringResource: Int){

        chipView.text = getString(stringResource).firstCharacterIntoCapital()

        chipView.setChipTextColorFromAttrRes(colorResource)
        val backgroundRes = android.R.attr.colorBackground
        chipView.setChipBackgroundColorFromAttrRes(backgroundRes)
        chipView.setChipStrokeColorFromAttrRes(colorResource)
        chipView.setChipIconTintFromAttrRes(colorResource)
        chipView.setChipIconResource(drawableResource)
        chipView.isChipIconVisible = true
    }

    private fun getVeggieOnClickListener(foodProduct: FoodBarcodeAnalysis) = View.OnClickListener {
        val intent = createStartActivityIntent(requireContext(), VeggieActivity::class).apply {
            putExtra(BARCODE_ANALYSIS_KEY, foodProduct)
        }

        startActivity(intent)
    }
}