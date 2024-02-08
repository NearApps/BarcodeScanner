package com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutBdd

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemAboutBinding
import com.github.nearapps.barcodescanner.presentation.intent.createSearchUrlIntent

class BddItemHolder(private val viewBinding: RecyclerViewItemAboutBinding)
    : RecyclerView.ViewHolder(viewBinding.root) {

    private val context = itemView.context

    fun updateItem(bdd: Bdd) {
        viewBinding.recyclerViewItemAboutTitleTextView.text = context.getString(bdd.nameResource)
        viewBinding.recyclerViewItemAboutLicenseTextView.visibility = View.GONE
        viewBinding.recyclerViewItemAboutAuthorTextView.visibility = View.GONE
        viewBinding.recyclerViewItemAboutDescriptionTextView.text = context.getString(bdd.descriptionResource)

        itemView.setOnClickListener {
            val url = context.getString(bdd.webLinkResource)
            val intent: Intent = createSearchUrlIntent(url)
            context.startActivity(intent)
        }
    }
}