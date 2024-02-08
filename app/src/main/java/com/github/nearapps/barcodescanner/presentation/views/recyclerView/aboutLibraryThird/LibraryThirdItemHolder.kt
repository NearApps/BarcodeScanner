package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutLibraryThird

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding
import com.github.nearapps.barcodescanner.presentation.intent.createSearchUrlIntent

class LibraryThirdItemHolder(private val viewBinding: RecyclerViewItemAboutBinding)
    : RecyclerView.ViewHolder(viewBinding.root) {

    private val context = itemView.context

    fun updateItem(libraryThird: LibraryThird) {
        viewBinding.recyclerViewItemAboutTitleTextView.text = context.getString(libraryThird.nameResource)
        viewBinding.recyclerViewItemAboutLicenseTextView.text = context.getString(libraryThird.licenseResource)
        viewBinding.recyclerViewItemAboutAuthorTextView.text = context.getString(R.string.dependency_by, context.getString(libraryThird.authorResource))
        viewBinding.recyclerViewItemAboutDescriptionTextView.text = context.getString(libraryThird.descriptionResource)

        itemView.setOnClickListener {
            val url = context.getString(libraryThird.webLinkResource)
            val intent: Intent = createSearchUrlIntent(url)
            context.startActivity(intent)
        }
    }
}