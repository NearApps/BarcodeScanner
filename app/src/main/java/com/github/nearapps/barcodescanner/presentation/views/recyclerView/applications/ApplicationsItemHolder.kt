package com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemApplicationsBinding
import java.lang.ref.WeakReference

/**
 * Représente une ligne (TableRow) d'un tableau (Table) qui est gérer par un Adapter (IngredientsAdapter).
 */
class ApplicationsItemHolder(private val viewBinding: RecyclerViewItemApplicationsBinding)
    : RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener {

    private var weakRefCallback: WeakReference<ApplicationsItemAdapter.OnApplicationItemListener>? = null
    private var item: ApplicationsItem? = null

    init {
        itemView.rootView.apply {
            setOnClickListener(this@ApplicationsItemHolder)
        }
    }

    fun updateItem(applicationsItem: ApplicationsItem, listener: ApplicationsItemAdapter.OnApplicationItemListener) {
        viewBinding.recyclerViewItemApplicationsTitleTextView.text = applicationsItem.title
        viewBinding.recyclerViewItemApplicationsPkgTextView.text = applicationsItem.pkg
        viewBinding.recyclerViewItemApplicationsImageView.setImageDrawable(applicationsItem.drawable)

        this.weakRefCallback = WeakReference(listener)
        this.item = applicationsItem
    }

    override fun onClick(v: View?) {
        item?.let {
            this.weakRefCallback?.get()?.onItemClick(v, it)
        }
    }
}