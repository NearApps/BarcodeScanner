package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.contents

import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.google.zxing.client.result.ParsedResult
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

abstract class AbstractBarcodeMatrixFragment : BarcodeAnalysisFragment<BarcodeAnalysis>() {

    override fun start(analysis: BarcodeAnalysis) {
        start(
            product = analysis,
            parsedResult = get {
                parametersOf(analysis.barcode.contents, analysis.barcode.getBarcodeFormat())
            }
        )
    }

    abstract fun start(product: BarcodeAnalysis, parsedResult: ParsedResult)
}