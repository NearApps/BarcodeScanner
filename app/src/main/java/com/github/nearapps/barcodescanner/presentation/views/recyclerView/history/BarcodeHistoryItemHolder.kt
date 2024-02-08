package com.github.nearapps.barcodescanner.presentation.views.recyclerView.history

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.getColorStateListFromAttrRes
import com.github.nearapps.barcodescanner.common.extensions.getDisplayName
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemHistoryBarcodeBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.google.android.material.card.MaterialCardView
import com.google.zxing.BarcodeFormat
import java.lang.ref.WeakReference

class BarcodeHistoryItemHolder(private val viewBinding: RecyclerViewItemHistoryBarcodeBinding)
    : RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener, View.OnLongClickListener {

    private var weakRefCallback: WeakReference<BarcodeHistoryItemAdapter.OnBarcodeItemListener>? = null
    private lateinit var item: BarcodeHistoryItem

    init {
        itemView.findViewById<MaterialCardView>(R.id.recycler_view_item_history_barcode_foreground_layout).apply {
            setOnClickListener(this@BarcodeHistoryItemHolder)
            setOnLongClickListener(this@BarcodeHistoryItemHolder)
        }
    }

    fun update(item: BarcodeHistoryItem, listener: BarcodeHistoryItemAdapter.OnBarcodeItemListener){

        val barcode: Barcode = item.barcode

        val barcodeType = barcode.getBarcodeType()

        // ---- Product Type Title TextView ----
        val entitled = if(barcode.name != "") barcode.name else itemView.context.getString(barcodeType.stringResource)
        viewBinding.recyclerViewItemHistoryBarcodeEntitledTextView.text = entitled

        // ---- Product Type Icon ----
        val drawableResource: Int = when {
            barcodeType != BarcodeType.UNKNOWN -> barcodeType.drawableResource
            barcode.is1DProductBarcodeFormat || barcode.is1DIndustrialBarcodeFormat -> R.drawable.ic_bar_code_24
            else -> R.drawable.baseline_qr_code_24
        }

        viewBinding.recyclerViewItemHistoryBarcodeImageView.setImageResource(drawableResource)

        // ---- Barcode Icon ----
        val barcodeIconDrawableResource: Int = when {
            barcode.is1DProductBarcodeFormat || barcode.is1DIndustrialBarcodeFormat -> R.drawable.ic_bar_code_24
            barcode.is2DBarcodeFormat -> {
                when(barcode.getBarcodeFormat()){
                    BarcodeFormat.AZTEC -> R.drawable.ic_aztec_code_24
                    BarcodeFormat.DATA_MATRIX -> R.drawable.ic_data_matrix_code_24
                    BarcodeFormat.PDF_417 -> R.drawable.ic_pdf_417_code_24
                    else -> R.drawable.baseline_qr_code_24
                }
            }
            else -> R.drawable.baseline_qr_code_24
        }
        viewBinding.recyclerViewItemHistoryBarcodeIconImageView.setImageResource(barcodeIconDrawableResource)
        viewBinding.recyclerViewItemHistoryBarcodeFormatTextView.text = barcode.getBarcodeFormat().getDisplayName(itemView.context)

        // ---- Content barcode TextView ----
        viewBinding.recyclerViewItemHistoryBarcodeContentTextView.text = barcode.contents

        // ---- Country ----
        val origin = barcode.country
        if(origin!=null){
            viewBinding.recyclerViewItemHistoryBarcodeOriginFlagImageView.setImageResource(origin.drawableResource)
            viewBinding.recyclerViewItemHistoryBarcodeOriginFlagImageView.visibility = View.VISIBLE
        }else{
            viewBinding.recyclerViewItemHistoryBarcodeOriginFlagImageView.setImageDrawable(null)
            viewBinding.recyclerViewItemHistoryBarcodeOriginFlagImageView.visibility = View.GONE
        }

        // ---- Date ----
        val date = barcode.scanDate
        viewBinding.recyclerViewItemHistoryBarcodeDateTextView.text = getDateAgo(date)

        // ---- Item CardView Selected ----
        viewBinding.recyclerViewItemHistoryBarcodeForegroundLayout.backgroundTintList = if(item.isSelected){
            itemView.context.getColorStateListFromAttrRes(R.attr.colorSurfaceVariant)
        } else null

        this.item=item
        this.weakRefCallback = WeakReference(listener)
    }

    fun getForegroundLayout() = viewBinding.recyclerViewItemHistoryBarcodeForegroundLayout
    //fun getBackgroundLayout() = viewBinding.recyclerViewItemHistoryBackgroundLayout

    /**
     * Permet d'avoir l'affichage textuel d'il y a combien de temps que le scan a eu lieu
     */
    private fun getDateAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS)

        return ago.toString()
    }

    override fun onClick(v: View?) {
        this.weakRefCallback?.get()?.let { barcodeItemListener ->
            if(barcodeItemListener.isSelectedMode()) {
                selectItem(v, barcodeItemListener)
            } else {
                barcodeItemListener.onItemClick(v, item.barcode)
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        this.weakRefCallback?.get()?.let { barcodeItemListener ->
            selectItem(v, barcodeItemListener)
        }
        return true
    }

    private fun selectItem(v: View?, barcodeItemListener: BarcodeHistoryItemAdapter.OnBarcodeItemListener) {
        item.isSelected = !item.isSelected
        barcodeItemListener.onItemSelect(v, item.barcode, item.isSelected)

        viewBinding.recyclerViewItemHistoryBarcodeForegroundLayout.backgroundTintList = if(item.isSelected){
            itemView.context.getColorStateListFromAttrRes(R.attr.colorSurfaceVariant)
        } else null
    }
}