package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.ActivityAboutLibraryThirdBinding
import com.github.nearapps.barcodescanner.presentation.customView.MarginItemDecoration
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutLibraryThird.LibraryThirdItemAdapter

class AboutLibraryThirdActivity : BaseActivity() {

    private val viewBinding: ActivityAboutLibraryThirdBinding by lazy { ActivityAboutLibraryThirdBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityAboutLibraryThirdToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)// On affiche l'icone "retour"

        configureRecyclerView()

        setContentView(viewBinding.root)
    }

    private fun configureRecyclerView(){

        val recyclerView = viewBinding.activityAboutLibraryThirdRecyclerView

        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        val adapter = LibraryThirdItemAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.standard_margin)))
    }
}