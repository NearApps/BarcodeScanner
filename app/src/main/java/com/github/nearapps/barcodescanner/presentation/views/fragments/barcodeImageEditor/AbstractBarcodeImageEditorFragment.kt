package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeImageEditor

import android.util.Log
import com.github.nearapps.barcodescanner.presentation.views.activities.BarcodeDetailsActivity
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment

abstract class AbstractBarcodeImageEditorFragment : BaseFragment() {
    fun onBarcodeDetailsActivity(task: (activity: BarcodeDetailsActivity) -> Unit) {
        val activity = requireActivity()
        if(activity is BarcodeDetailsActivity) {
            task(activity)
        } else {
            Log.e("Error", "Fragment is not attached to BarcodeDetailsActivity!")
        }
    }
}