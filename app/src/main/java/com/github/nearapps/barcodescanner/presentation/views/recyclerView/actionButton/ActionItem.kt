package com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ActionItem(@StringRes val textRes: Int, @DrawableRes val imageRes: Int, val listener: OnActionItemListener) {
    interface OnActionItemListener {
        fun onItemClick(view: View?)
    }
}