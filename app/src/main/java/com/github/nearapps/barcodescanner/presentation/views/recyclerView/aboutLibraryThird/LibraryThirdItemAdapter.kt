package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutLibraryThird

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding

class LibraryThirdItemAdapter: RecyclerView.Adapter<LibraryThirdItemHolder>() {

    private val libraryThirdArray: Array<LibraryThird> = LibraryThird.entries.toTypedArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryThirdItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val viewBinding = RecyclerViewItemAboutBinding.inflate(layoutInflater, parent, false)

        return LibraryThirdItemHolder(viewBinding)
    }

    override fun getItemCount(): Int = libraryThirdArray.size

    override fun onBindViewHolder(holder: LibraryThirdItemHolder, position: Int) {
        holder.updateItem(libraryThirdArray[position])
    }
}