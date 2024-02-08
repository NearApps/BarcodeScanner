package com.github.nearapps.barcodescanner.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.nearapps.barcodescanner.R

class ExpandableCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val headerFrameLayout: FrameLayout
    private val bodyFrameLayout: FrameLayout

    private var nbViewsAdded = 0

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.template_expandable_card_view, this, true)

        val expandableView: ExpandableView = view.findViewById(R.id.template_expandable_card_view_expandable_view)
        headerFrameLayout = view.findViewById(R.id.template_expandable_card_view_header_frame_layout)
        bodyFrameLayout = view.findViewById(R.id.template_expandable_card_view_body_frame_layout)

        context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableCardView, defStyleAttr, defStyleRes).apply {
            try {
                getBoolean(R.styleable.ExpandableCardView_isOpen, false).let { isOpen ->
                    if(isOpen) expandableView.open() else expandableView.close()
                }
            } finally {
                recycle()
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        when(nbViewsAdded++) {
            0 -> super.addView(child, index, params) // MaterialCardView (root view) from template_expandable_card_view.xml
            1 -> headerFrameLayout.addView(child) // Header
            2 -> bodyFrameLayout.addView(child) // Body
            else -> throw Exception("ExpandableCardView must have two children")
        }
    }
}