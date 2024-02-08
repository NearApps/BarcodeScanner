package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.view.View
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem

class PetFoodActionsFragment: AbstractActionsFragment() {
    override fun configureActions(barcode: Barcode): Array<ActionItem> {
        return when(barcode.getBarcodeType()){
            BarcodeType.PET_FOOD -> configurePetFoodActions(barcode)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configurePetFoodActions(barcode: Barcode) = arrayOf(
        ActionItem(R.string.action_web_search_label, R.drawable.baseline_search_24, showUrlsAlertDialog(barcode.contents)),
        ActionItem(R.string.share_text_label, R.drawable.baseline_share_24, shareTextContents(barcode.contents)),
        ActionItem(R.string.copy_barcode_label, R.drawable.baseline_content_copy_24, copyContents(barcode.contents)),
        ActionItem(R.string.action_modify_barcode, R.drawable.baseline_create_24, modifyBarcodeContents(barcode))
    )

    // Actions

    private fun showUrlsAlertDialog(contents: String): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val openPetFoodFactsUrl = getString(R.string.search_engine_open_pet_food_facts_product_url, contents)

            val items = arrayOf<Pair<String, ActionItem.OnActionItemListener>>(
                Pair(getString(R.string.action_web_search_label), openContentsWithSearchEngine(contents)),
                Pair(getString(R.string.action_product_search_label, getString(R.string.open_pet_food_facts_label)), openUrl(openPetFoodFactsUrl))
            )

            createAlertDialog(requireContext(), getString(R.string.search_label), items).show()
        }
    }
}