package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.defaultAnalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentDefaultBarcodeAnalysisBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.DefaultBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.about.BarcodeAboutOverviewFragment
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class DefaultBarcodeAnalysisFragment : BarcodeAnalysisFragment<DefaultBarcodeAnalysis>() {
    private var _binding: FragmentDefaultBarcodeAnalysisBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDefaultBarcodeAnalysisBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun configureMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_activity_barcode_analysis, menu)

                // On retire les menus inutiles
                menu.removeItem(R.id.menu_activity_barcode_analysis_download_from_apis)
                menu.removeItem(R.id.menu_activity_barcode_analysis_product_source_api_info_item)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId) {
                R.id.menu_activity_barcode_analysis_about_barcode_item -> {
                    startBarcodeDetailsActivity()
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun start(analysis: DefaultBarcodeAnalysis) {
        viewBinding.fragmentDefaultBarcodeAnalysisOuterView.fixAnimateLayoutChangesInNestedScroll()
        configureBarcodeAboutOverviewFragment()
    }

    private fun configureBarcodeAboutOverviewFragment() = applyFragment(
        containerViewId = viewBinding.fragmentDefaultBarcodeAnalysisBarcodeAboutOverviewFrameLayout.id,
        fragmentClass = BarcodeAboutOverviewFragment::class,
        args = arguments
    )

    companion object {
        fun newInstance(barcodeAnalysis: DefaultBarcodeAnalysis) = DefaultBarcodeAnalysisFragment().apply {
            arguments = get<Bundle>().apply {
                putSerializable(BARCODE_ANALYSIS_KEY, barcodeAnalysis)
            }
        }
    }
}