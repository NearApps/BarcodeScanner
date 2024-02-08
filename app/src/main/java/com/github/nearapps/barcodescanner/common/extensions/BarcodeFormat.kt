package com.github.nearapps.barcodescanner.common.extensions

import android.content.Context
import com.github.nearapps.barcodescanner.R
import com.google.zxing.BarcodeFormat

fun BarcodeFormat.getDisplayName(context: Context): String = when (this) {
    BarcodeFormat.QR_CODE -> context.getString(R.string.barcode_qr_code_label)
    BarcodeFormat.DATA_MATRIX -> context.getString(R.string.barcode_data_matrix_label)
    BarcodeFormat.PDF_417 -> context.getString(R.string.barcode_pdf_417_label)
    BarcodeFormat.AZTEC -> context.getString(R.string.barcode_aztec_label)
    BarcodeFormat.EAN_13 -> context.getString(R.string.barcode_ean_13_label)
    BarcodeFormat.EAN_8 -> context.getString(R.string.barcode_ean_8_label)
    BarcodeFormat.UPC_A -> context.getString(R.string.barcode_upc_a_label)
    BarcodeFormat.UPC_E -> context.getString(R.string.barcode_upc_e_label)
    BarcodeFormat.CODE_128 -> context.getString(R.string.barcode_code_128_label)
    BarcodeFormat.CODE_93 -> context.getString(R.string.barcode_code_93_label)
    BarcodeFormat.CODE_39 -> context.getString(R.string.barcode_code_39_label)
    BarcodeFormat.CODABAR -> context.getString(R.string.barcode_codabar_label)
    BarcodeFormat.ITF -> context.getString(R.string.barcode_itf_label)
    else -> this.name.replace("_", " ")
}

fun BarcodeFormat.is1DProductBarcode(): Boolean {
    return when(this){
        BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.UPC_EAN_EXTENSION,
        BarcodeFormat.UPC_A, BarcodeFormat.UPC_E -> true
        else -> false
    }
}

fun BarcodeFormat.is1DIndustrialBarcode(): Boolean {
    return when(this){
        BarcodeFormat.CODE_128, BarcodeFormat.CODABAR,
        BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.ITF -> true
        else -> false
    }
}

fun BarcodeFormat.is2DBarcode(): Boolean {
    return when(this){
        BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX,
        BarcodeFormat.MAXICODE, BarcodeFormat.PDF_417,
        BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED -> true
        else -> false
    }
}