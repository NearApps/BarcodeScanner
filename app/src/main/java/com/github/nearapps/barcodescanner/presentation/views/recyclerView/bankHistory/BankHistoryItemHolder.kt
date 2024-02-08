package com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.getColorStateListFromAttrRes
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemHistoryBankBinding
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.google.android.material.card.MaterialCardView
import java.lang.ref.WeakReference

class BankHistoryItemHolder(private val viewBinding: RecyclerViewItemHistoryBankBinding)
    : RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener, View.OnLongClickListener {

    private var weakRefCallback: WeakReference<BankHistoryItemAdapter.OnBankItemListener>? = null
    private lateinit var item: BankHistoryItem

    init {
        itemView.findViewById<MaterialCardView>(R.id.recycler_view_item_history_bank_foreground_layout).apply {
            setOnClickListener(this@BankHistoryItemHolder)
            setOnLongClickListener(this@BankHistoryItemHolder)
        }
    }

    fun update(item: BankHistoryItem, listener: BankHistoryItemAdapter.OnBankItemListener){
        viewBinding.recyclerViewItemHistoryBankBicLabel.apply {
            val bicLabel = context.getString(R.string.qr_code_text_input_edit_text_hint_epc_bic)
            text = context.getString(R.string.text_colon, bicLabel)
        }
        viewBinding.recyclerViewItemHistoryBankIbanLabel.apply {
            val ibanLabel = context.getString(R.string.qr_code_text_input_edit_text_hint_epc_iban)
            text = context.getString(R.string.text_colon, ibanLabel)
        }

        val bank: Bank = item.bank
        viewBinding.recyclerViewItemHistoryBankNameTextView.text = bank.name
        viewBinding.recyclerViewItemHistoryBankBicTextView.text = bank.bic
        viewBinding.recyclerViewItemHistoryBankIbanTextView.text = bank.iban

        if(bank.bic.isEmpty()) {
            viewBinding.recyclerViewItemHistoryBankBicLayout.visibility = View.GONE
        }

        // ---- Item CardView Selected ----
        viewBinding.recyclerViewItemHistoryBankForegroundLayout.backgroundTintList = if(item.isSelected){
            itemView.context.getColorStateListFromAttrRes(R.attr.colorSurfaceVariant)
        } else null

        this.item = item
        this.weakRefCallback = WeakReference(listener)
    }

    fun getForegroundLayout() = viewBinding.recyclerViewItemHistoryBankForegroundLayout

    override fun onClick(v: View?) {
        this.weakRefCallback?.get()?.let { bankItemListener ->
            if(bankItemListener.isSelectedMode()) {
                selectItem(v, bankItemListener)
            } else {
                bankItemListener.onItemClick(v, item.bank)
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        this.weakRefCallback?.get()?.let { barcodeItemListener ->
            selectItem(v, barcodeItemListener)
        }
        return true
    }

    private fun selectItem(v: View?, barcodeItemListener: BankHistoryItemAdapter.OnBankItemListener) {
        item.isSelected = !item.isSelected
        barcodeItemListener.onItemSelect(v, item.bank, item.isSelected)
        viewBinding.recyclerViewItemHistoryBankForegroundLayout.backgroundTintList = if(item.isSelected){
            itemView.context.getColorStateListFromAttrRes(R.attr.colorSurfaceVariant)
        } else null
    }
}