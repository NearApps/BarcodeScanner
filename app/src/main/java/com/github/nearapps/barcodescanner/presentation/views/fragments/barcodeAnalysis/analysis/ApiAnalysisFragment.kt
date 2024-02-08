package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.intent.createSearchUrlIntent
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment

abstract class ApiAnalysisFragment<T: BarcodeAnalysis>: BarcodeAnalysisFragment<T>() {

    private var sourceApiInfoAlertDialog: AlertDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        sourceApiInfoAlertDialog?.dismiss()
    }

    private fun configureSourceApiInfoAlertDialog(titleResource: Int, layout: Int, urlResource: Int, barcodeContents: String) {
        sourceApiInfoAlertDialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(titleResource))
            setView(layout)
            setNegativeButton(R.string.close_dialog_label) { dialogInterface, _ -> dialogInterface.cancel() }
            setPositiveButton(R.string.go_to_dialog_label) { _, _ ->
                val intent: Intent = createSearchUrlIntent(getString(urlResource, barcodeContents))
                startActivity(intent)
            }
        }.create()
    }

    override fun configureMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_activity_barcode_analysis, menu)

                // On retire le menu pour rechercher dans les APIs, car ici la recherche à déjà été faite.
                menu.removeItem(R.id.menu_activity_barcode_analysis_download_from_apis)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId) {
                R.id.menu_activity_barcode_analysis_product_source_api_info_item -> {
                    sourceApiInfoAlertDialog?.show()
                    true
                }
                R.id.menu_activity_barcode_analysis_about_barcode_item -> {
                    startBarcodeDetailsActivity()
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun start(analysis: T) {
        configureSourceApiInfoAlertDialog(analysis.source.nameResource, analysis.source.layout, analysis.source.urlResource, analysis.barcode.contents)
    }
}