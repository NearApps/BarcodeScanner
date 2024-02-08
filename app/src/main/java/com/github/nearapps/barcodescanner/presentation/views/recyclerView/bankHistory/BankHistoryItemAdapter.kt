package com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemHistoryBankBinding
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank

class BankHistoryItemAdapter(private val callback: OnBankItemListener): RecyclerView.Adapter<BankHistoryItemHolder>() {

    interface OnBankItemListener {
        fun onItemClick(view: View?, bank: Bank)
        fun onItemSelect(view: View?, bank: Bank, isSelected: Boolean)
        fun isSelectedMode(): Boolean
    }

    private var items = listOf<BankHistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankHistoryItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemHistoryBankBinding.inflate(inflater, parent, false)
        return BankHistoryItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BankHistoryItemHolder, position: Int) {
        holder.update(items[position], callback)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(bankList: List<Bank>){
        items = bankList.map { BankHistoryItem(it) }
        this.notifyDataSetChanged()
    }
    
    fun getBank(position: Int): Bank = this.items[position].bank
}