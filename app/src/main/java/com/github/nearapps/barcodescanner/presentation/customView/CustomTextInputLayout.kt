package com.github.nearapps.barcodescanner.presentation.customView

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.children
import com.github.nearapps.barcodescanner.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Redéfinition permettant de changer la couleur de l'icone (app:startIconDrawable) lorsque le TextInputEditText enfant a le focus.
 */
class CustomTextInputLayout(context: Context, attrs: AttributeSet?): TextInputLayout(context, attrs){

    private val startIconTint: Int?
    private val startIconTintFocused: Int?

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout, 0, 0).apply {
            try {
                startIconTint = getColorStateList(R.styleable.CustomTextInputLayout_startIconTint)?.defaultColor
                startIconTintFocused = getColorStateList(R.styleable.CustomTextInputLayout_startIconTintFocused)?.defaultColor
            } finally {
                recycle()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setStartIconTintList(obtainColorStateList(startIconTint))

        if(childCount>0) {

            val subView = children.elementAt(0) // FrameLayout

            if(subView is FrameLayout){

                subView.children.forEach {

                    if(it is TextInputEditText) {
                        it.setOnFocusChangeListener { _, hasFocus ->

                            val colorStateList =
                                if (hasFocus)
                                    obtainColorStateList(startIconTintFocused)
                                else
                                    obtainColorStateList(startIconTint)

                            setStartIconTintList(colorStateList)
                        }
                    }
                }
            }
        }
    }

    private fun obtainColorStateList(resColor: Int?): ColorStateList? = if(resColor!=null) ColorStateList.valueOf(resColor) else null
}