package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.ActivityAboutApisBinding

class AboutApisActivity : BaseActivity() {

    private val viewBinding: ActivityAboutApisBinding by lazy {
        ActivityAboutApisBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityAboutApisToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)// On affiche l'icone "retour"
        configureViews()

        setContentView(viewBinding.root)
    }

    private fun configureViews() {
        viewBinding.activityAboutApisAutomaticSearchDescriptionTextView.text = getString(
            R.string.preferences_information_about_remote_api_automatic_search_description,
            getString(R.string.preferences_switch_scan_search_on_api_label)
        )
        viewBinding.activityAboutApisManualSearchDescriptionTextView.text = getString(
            R.string.preferences_information_about_remote_api_manual_search_description,
            getString(R.string.preferences_switch_scan_search_on_api_label)
        )
    }
}