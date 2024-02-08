package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeImageEditor

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_IMAGE_BACKGROUND_COLOR_KEY
import com.github.nearapps.barcodescanner.common.utils.BARCODE_IMAGE_FRONT_COLOR_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeImageEditorColorsBinding
import com.atharok.colorpicker.ColorPickerDialog

class BarcodeImageEditorColorsFragment : AbstractBarcodeImageEditorFragment() {

    private var _binding: FragmentBarcodeImageEditorColorsBinding? = null
    private val viewBinding get() = _binding!!

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeImageEditorColorsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        colorPickerDialog?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentBarcodeImageEditorColorsOuterView.fixAnimateLayoutChangesInNestedScroll()

        configureColor(
            container = viewBinding.fragmentBarcodeImageEditorColorsBackgroundCardView,
            imageView = viewBinding.fragmentBarcodeImageEditorColorsBackgroundImageView,
            title = viewBinding.fragmentBarcodeImageEditorColorsBackgroundTextView.text.toString(),
            initialColor = arguments?.getInt(BARCODE_IMAGE_BACKGROUND_COLOR_KEY, Color.WHITE) ?: Color.WHITE,
            updateBitmap = { color ->
                onBarcodeDetailsActivity { activity ->
                    activity.regenerateBitmap(backgroundColor = color)
                }
            }
        )

        configureColor(
            container = viewBinding.fragmentBarcodeImageEditorColorsForegroundCardView,
            imageView = viewBinding.fragmentBarcodeImageEditorColorsForegroundImageView,
            title = viewBinding.fragmentBarcodeImageEditorColorsForegroundTextView.text.toString(),
            initialColor = arguments?.getInt(BARCODE_IMAGE_FRONT_COLOR_KEY, Color.BLACK) ?: Color.BLACK,
            updateBitmap = { color ->
                onBarcodeDetailsActivity { activity ->
                    activity.regenerateBitmap(frontColor = color)
                }
            }
        )
    }

    private fun configureColor(
        container: View,
        imageView: ImageView,
        title: String,
        @ColorInt initialColor: Int,
        updateBitmap: (color: Int) -> Unit
    ) {
        @ColorInt var currentColor = initialColor
        imageView.setColorFilter(initialColor)
        container.setOnClickListener {
            colorPickerDialog = ColorPickerDialog.Builder(requireActivity())
                .setTitle(title)
                .setDefaultColor(currentColor)
                .setPositiveButton { color: Int, _: String ->
                    currentColor = color
                    imageView.setColorFilter(color)
                    updateBitmap(color)
                }
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            @ColorInt frontColor: Int = Color.BLACK,
            @ColorInt backgroundColor: Int = Color.WHITE
        ) = BarcodeImageEditorColorsFragment().apply {
            arguments = Bundle().apply {
                putInt(BARCODE_IMAGE_FRONT_COLOR_KEY, frontColor)
                putInt(BARCODE_IMAGE_BACKGROUND_COLOR_KEY, backgroundColor)
            }
        }
    }
}