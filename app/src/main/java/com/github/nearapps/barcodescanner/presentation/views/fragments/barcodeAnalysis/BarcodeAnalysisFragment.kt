package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.github.nearapps.barcodescanner.common.extensions.serializable
import com.github.nearapps.barcodescanner.common.utils.BARCODE_ANALYSIS_KEY
import com.github.nearapps.barcodescanner.common.utils.BARCODE_CONTENTS_KEY
import com.github.nearapps.barcodescanner.common.utils.BARCODE_FORMAT_KEY
import com.github.nearapps.barcodescanner.common.utils.QR_CODE_ERROR_CORRECTION_LEVEL_KEY
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeDetailsActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.templates.ExpandableCardViewFragment

abstract class BarcodeAnalysisFragment<T: BarcodeAnalysis>: BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()

        arguments?.takeIf {
            // Si les données ResultScanData sont bien stockées en mémoire
            it.containsKey(BARCODE_ANALYSIS_KEY)// && it.getSerializable(PRODUCT_KEY) is BarcodeProduct
        }?.apply {

            serializable(BARCODE_ANALYSIS_KEY, BarcodeAnalysis::class.java)?.let { barcodeAnalysis ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    start(barcodeAnalysis as T)
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }
        }
    }

    protected open fun configureMenu() {}

    abstract fun start(analysis: T)

    protected fun configureExpandableCardViewFragment(
        frameLayout: FrameLayout,
        title: String,
        contents: CharSequence?,
        iconDrawableResource: Int? = null
    ) {
        if(!contents.isNullOrBlank()) {
            applyFragment(
                containerViewId = frameLayout.id,
                fragment = ExpandableCardViewFragment.newInstance(
                    title = title,
                    contents = contents.trim(),
                    drawableResource = iconDrawableResource
                )
            )
        } else {
            frameLayout.visibility = View.GONE
        }
    }

    protected fun startBarcodeDetailsActivity() {
        arguments?.serializable(BARCODE_ANALYSIS_KEY, BarcodeAnalysis::class.java)?.let { barcodeAnalysis ->
            val intent = createStartActivityIntent(requireContext(), BarcodeDetailsActivity::class).apply {
                putExtra(BARCODE_CONTENTS_KEY, barcodeAnalysis.barcode.contents)
                putExtra(BARCODE_FORMAT_KEY, barcodeAnalysis.barcode.formatName)
                putExtra(QR_CODE_ERROR_CORRECTION_LEVEL_KEY, barcodeAnalysis.barcode.errorCorrectionLevel)
            }
            startActivity(intent)
        }
    }
}