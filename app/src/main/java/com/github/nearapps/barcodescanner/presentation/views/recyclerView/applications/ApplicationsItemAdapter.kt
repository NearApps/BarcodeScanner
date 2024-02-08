package com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemApplicationsBinding

class ApplicationsItemAdapter(private val callback: OnApplicationItemListener) : RecyclerView.Adapter<ApplicationsItemHolder>() {

    interface OnApplicationItemListener {
        fun onItemClick(view: View?, item: ApplicationsItem)
    }

    var applications: List<ApplicationsItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationsItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemApplicationsBinding.inflate(layoutInflater, parent, false)
        return ApplicationsItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = applications.size

    override fun onBindViewHolder(holder: ApplicationsItemHolder, position: Int) {
        holder.updateItem(applications[position], callback)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(applications: List<ApplicationsItem>){
        this.applications = applications
        this.notifyDataSetChanged()
    }
}