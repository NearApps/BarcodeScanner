package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeAboutOverviewBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.AbstractActionsFragment
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

/**
 * Contains:
 * - Barcode content (BarcodeAboutContentsFragment)
 * - Additional information (BarcodeAboutMoreInfoFragment)
 * - Actions (subclass of AbstractActionsFragment)
 */
class BarcodeAboutOverviewFragment : BarcodeAnalysisFragment<BarcodeAnalysis>() {

    private var _binding: FragmentBarcodeAboutOverviewBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeAboutOverviewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: BarcodeAnalysis) {
        viewBinding.root.fixAnimateLayoutChangesInNestedScroll()
        configureBarcodeAboutContentsFragment()
        configureBarcodeAboutMoreInfoFragment()
        configureBarcodeActionsFragment(analysis.barcode.getBarcodeType())
    }

    private fun configureBarcodeAboutContentsFragment() = applyFragment(
        containerViewId = viewBinding.fragmentBarcodeAboutOverviewBarcodeContentsFrameLayout.id,
        fragmentClass = BarcodeAboutContentsFragment::class,
        args = arguments
    )

    private fun configureBarcodeAboutMoreInfoFragment() = applyFragment(
        containerViewId = viewBinding.fragmentBarcodeAboutOverviewMoreInfoFrameLayout.id,
        fragmentClass = BarcodeAboutMoreInfoFragment::class,
        args = arguments
    )

    private fun configureBarcodeActionsFragment(barcodeType: BarcodeType) = applyFragment(
        containerViewId = viewBinding.fragmentBarcodeAboutOverviewBarcodeActionsFrameLayout.id,
        fragmentClass = get<KClass<out AbstractActionsFragment>> { parametersOf(barcodeType) },
        args = arguments
    )
}