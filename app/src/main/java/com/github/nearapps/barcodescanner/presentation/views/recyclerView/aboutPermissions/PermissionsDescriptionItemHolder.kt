package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutPermissions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding

class PermissionsDescriptionItemHolder(private val viewBinding: RecyclerViewItemAboutBinding)
    : RecyclerView.ViewHolder(viewBinding.root) {

    private val context = itemView.context

    fun updateItem(permissionsDescription: PermissionsDescription) {
        viewBinding.recyclerViewItemAboutTitleTextView.text = context.getString(permissionsDescription.nameResource)
        viewBinding.recyclerViewItemAboutLicenseTextView.visibility = View.GONE
        viewBinding.recyclerViewItemAboutAuthorTextView.visibility = View.GONE
        viewBinding.recyclerViewItemAboutDescriptionTextView.text = context.getString(permissionsDescription.descriptionResource)
    }
}