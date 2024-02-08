package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.getDisplayName
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeAboutMoreInfoBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.google.zxing.BarcodeFormat

/**
 * Contains additional information about the barcode:
 * Format, Country, Origin, Error Correction Level, and Description.
 */
class BarcodeAboutMoreInfoFragment : BarcodeAnalysisFragment<BarcodeAnalysis>() {

    private var _binding: FragmentBarcodeAboutMoreInfoBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeAboutMoreInfoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: BarcodeAnalysis) {
        configureHeaderEntitledAndIcon()
        configureFormat(analysis)
        configureOrigin(analysis)
        configureErrorCorrectionLevel(analysis)
        configureDescription(analysis)
    }

    private fun configureHeaderEntitledAndIcon() {
        viewBinding.fragmentBarcodeAboutMoreInfoHeaderTextView.text = getString(R.string.about_barcode_information_label)
    }

    private fun configureFormat(barcodeAnalysis: BarcodeAnalysis) {
        val formatName = barcodeAnalysis.barcode.getBarcodeFormat().getDisplayName(requireContext())
        val format = getString(R.string.about_barcode_format_label, formatName)
        viewBinding.fragmentBarcodeAboutMoreInfoBodyFormatTextView.text = format
    }

    private fun configureOrigin(barcodeAnalysis: BarcodeAnalysis) {
        val origin = barcodeAnalysis.barcode.country

        if(origin != null) {
            viewBinding.fragmentBarcodeAboutMoreInfoBodyOriginFlagImageView.setImageResource(origin.drawableResource)
            displayText(
                textView = viewBinding.fragmentBarcodeAboutMoreInfoBodyOriginCountryTextView,
                layout = viewBinding.fragmentBarcodeAboutMoreInfoBodyOriginLayout,
                text = getString(origin.stringResource)
            )
        } else {
            viewBinding.fragmentBarcodeAboutMoreInfoBodyOriginLayout.visibility = View.GONE
        }
    }

    private fun configureErrorCorrectionLevel(barcodeAnalysis: BarcodeAnalysis) {
        val text = when(barcodeAnalysis.barcode.getBarcodeFormat()) {
            BarcodeFormat.QR_CODE -> {
                val errorCorrectionLevel = barcodeAnalysis.barcode.getQrCodeErrorCorrectionLevel()
                if(errorCorrectionLevel != QrCodeErrorCorrectionLevel.NONE) {
                    val errorCorrectionLevelLabel = getString(R.string.qr_code_error_correction_level_label)
                    val entitled = getString(R.string.text_colon, errorCorrectionLevelLabel)
                    "$entitled ${getString(errorCorrectionLevel.stringResource)}"
                } else null
            }
            else -> null
        }

        displayText(
            textView = viewBinding.fragmentBarcodeAboutMoreInfoBodyErrorCorrectionLevelTextView,
            layout = viewBinding.fragmentBarcodeAboutMoreInfoBodyErrorCorrectionLevelLayout,
            text = text
        )
    }

    private fun configureDescription(barcodeAnalysis: BarcodeAnalysis) {
        val text = when(barcodeAnalysis.barcode.getBarcodeFormat()) {
            BarcodeFormat.UPC_A -> getString(R.string.barcode_upc_a_description_label)
            BarcodeFormat.UPC_E -> getString(R.string.barcode_upc_e_description_label)
            BarcodeFormat.EAN_13 -> getString(R.string.barcode_ean_13_description_label)
            BarcodeFormat.EAN_8 -> getString(R.string.barcode_ean_8_description_label)
            BarcodeFormat.CODE_39 -> getString(R.string.barcode_code_39_description_label)
            BarcodeFormat.CODE_93 -> getString(R.string.barcode_code_93_description_label)
            BarcodeFormat.CODE_128 -> getString(R.string.barcode_code_128_description_label)
            BarcodeFormat.CODABAR -> getString(R.string.barcode_codabar_description_label)
            BarcodeFormat.ITF -> getString(R.string.barcode_itf_description_label)
            else -> null
        }

        displayText(
            textView = viewBinding.fragmentBarcodeAboutMoreInfoBodyDescriptionTextView,
            layout = viewBinding.fragmentBarcodeAboutMoreInfoBodyDescriptionLayout,
            text = text
        )
    }
}