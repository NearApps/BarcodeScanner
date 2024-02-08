package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.ActivityAboutPermissionsDescriptionBinding
import com.github.nearapps.barcodescanner.presentation.customView.MarginItemDecoration
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.aboutPermissions.PermissionsDescriptionItemAdapter

class AboutPermissionsDescriptionActivity : BaseActivity() {
    private val viewBinding: ActivityAboutPermissionsDescriptionBinding by lazy { ActivityAboutPermissionsDescriptionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityAboutPermissionsDescriptionToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)// On affiche l'icone "retour"

        configureRecyclerView()

        setContentView(viewBinding.root)
    }

    private fun configureRecyclerView(){

        val recyclerView = viewBinding.activityAboutPermissionsDescriptionRecyclerView

        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        val adapter = PermissionsDescriptionItemAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.standard_margin)))
    }
}