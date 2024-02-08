package com.github.nearapps.barcodescanner.presentation.views.recyclerView.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemImageBinding

class ImageAdapter(private val imageUriList: List<String>): RecyclerView.Adapter<ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemImageBinding.inflate(layoutInflater, parent, false)

        return ImageHolder(viewBinding)
    }

    override fun getItemCount(): Int = imageUriList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.updateItem(imageUriList[position])
    }
}