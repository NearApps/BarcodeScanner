package com.github.nearapps.barcodescanner.domain.entity.barcode

import com.github.nearapps.barcodescanner.R
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.Serializable

enum class QrCodeErrorCorrectionLevel(val stringResource: Int, val errorCorrectionLevel: ErrorCorrectionLevel?): Serializable {
    L(R.string.qr_code_error_correction_level_name_low, ErrorCorrectionLevel.L),
    M(R.string.qr_code_error_correction_level_name_medium, ErrorCorrectionLevel.M),
    Q(R.string.qr_code_error_correction_level_name_quartile, ErrorCorrectionLevel.Q),
    H(R.string.qr_code_error_correction_level_name_high, ErrorCorrectionLevel.H),
    NONE(R.string.empty, null)
}