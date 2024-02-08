package com.github.nearapps.barcodescanner.presentation.views.recyclerView.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemHistoryBarcodeBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode

class BarcodeHistoryItemAdapter(private val callback: OnBarcodeItemListener): RecyclerView.Adapter<BarcodeHistoryItemHolder>() {

    interface OnBarcodeItemListener {
        fun onItemClick(view: View?, barcode: Barcode)
        fun onItemSelect(view: View?, barcode: Barcode, isSelected: Boolean)
        fun isSelectedMode(): Boolean
    }

    private var items = listOf<BarcodeHistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeHistoryItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemHistoryBarcodeBinding.inflate(inflater, parent, false)
        return BarcodeHistoryItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BarcodeHistoryItemHolder, position: Int) {
        holder.update(items[position], callback)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(barcodeList: List<Barcode>){
        items = barcodeList.map { BarcodeHistoryItem(it) }
        this.notifyDataSetChanged()
    }
    
    fun getBarcode(position: Int): Barcode = this.items[position].barcode
}