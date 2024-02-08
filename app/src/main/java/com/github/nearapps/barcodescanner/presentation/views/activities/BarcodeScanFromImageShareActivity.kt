package com.github.nearapps.barcodescanner.presentation.views.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.github.nearapps.barcodescanner.common.extensions.parcelable
import com.github.nearapps.barcodescanner.common.utils.BARCODE_KEY
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.presentation.intent.createStartActivityIntent
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.google.zxing.Result
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class BarcodeScanFromImageShareActivity: BarcodeScanFromImageAbstractActivity() {

    private val databaseBarcodeViewModel: DatabaseBarcodeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = getImageUri()
        if(uri != null) configureCropManagement(uri)
    }

    /**
     * Si on récupère l'URI via un partage d'image d'une autre application (intent-filter)
     */
    private fun getImageUri(): Uri?  = if(intent?.action == Intent.ACTION_SEND) {
         intent.parcelable(Intent.EXTRA_STREAM, Uri::class.java)
    } else null

    override fun onSuccessfulImageScan(result: Result?) {
        result?.let {
            val contents = result.text
            val formatName = result.barcodeFormat?.name

            if(contents != null && formatName != null){

                val errorCorrectionLevel: QrCodeErrorCorrectionLevel =
                    get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT)) { parametersOf(result) }

                val barcode: Barcode = get { parametersOf(contents, formatName, errorCorrectionLevel) }

                if(settingsManager.shouldAddBarcodeScanToHistory) {
                    databaseBarcodeViewModel.insertBarcode(barcode)
                }

                val intent = createStartActivityIntent(this, BarcodeAnalysisActivity::class).apply {
                    putExtra(BARCODE_KEY, barcode)
                }

                startActivity(intent)

                finish()
            }
        }
    }
}