package com.github.nearapps.barcodescanner.presentation.views.recyclerView.images

import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.common.extensions.setImageFromWeb
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemImageBinding

class ImageHolder(private val viewBinding: RecyclerViewItemImageBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun updateItem(uri: String) {
        viewBinding.recyclerViewItemImageImageView.setImageFromWeb(uri)
    }
}