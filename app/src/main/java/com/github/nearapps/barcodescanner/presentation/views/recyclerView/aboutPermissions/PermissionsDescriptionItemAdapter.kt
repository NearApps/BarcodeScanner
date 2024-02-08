package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutPermissions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding

class PermissionsDescriptionItemAdapter: RecyclerView.Adapter<PermissionsDescriptionItemHolder>() {

    private val permissionsDescriptionArray: Array<PermissionsDescription> = PermissionsDescription.entries.toTypedArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionsDescriptionItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val viewBinding = RecyclerViewItemAboutBinding.inflate(layoutInflater, parent, false)

        return PermissionsDescriptionItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = permissionsDescriptionArray.size

    override fun onBindViewHolder(holder: PermissionsDescriptionItemHolder, position: Int) {
        holder.updateItem(permissionsDescriptionArray[position])
    }
}