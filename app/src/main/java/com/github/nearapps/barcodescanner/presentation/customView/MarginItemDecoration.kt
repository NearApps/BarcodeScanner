package com.github.nearapps.barcodescanner.presentation.customView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Solution pour gérer les marges de la RecyclerView à l'intérieur de celle-ci et pas à l'extérieur
 * comme avec layout_margin. Les marges s'appliquent autour des items à l'intérieur de la
 * RecyclerView et non autour de la RecyclerView elle-même.
 *
 * Implémentation:
 *      recyclerView.addItemDecoration(
 *          MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
 *      )
 *
 * Source de la solution : https://medium.com/@cesarmorigaki/a-better-way-to-set-recyclerview-items-margin-708ea9d3ac25
 */
class MarginItemDecoration(private val spaceSize: Int,
                           private val spanCount: Int = 1,
                           private val orientation: Int = LinearLayoutManager.VERTICAL)
    : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    top = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    left = spaceSize
                }
            } else {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    left = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    top = spaceSize
                }
            }

            right = spaceSize
            bottom = spaceSize
        }
    }
}