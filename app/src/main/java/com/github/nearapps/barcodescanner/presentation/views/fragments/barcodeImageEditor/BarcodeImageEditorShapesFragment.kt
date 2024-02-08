package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeImageEditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nearapps.barcodescanner.common.extensions.fixAnimateLayoutChangesInNestedScroll
import com.github.nearapps.barcodescanner.common.utils.BARCODE_IMAGE_CORNER_RADIUS_KEY
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeImageEditorShapesBinding

class BarcodeImageEditorShapesFragment : AbstractBarcodeImageEditorFragment() {

    private var _binding: FragmentBarcodeImageEditorShapesBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeImageEditorShapesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentBarcodeImageEditorShapesOuterView.fixAnimateLayoutChangesInNestedScroll()

        viewBinding.fragmentBarcodeImageEditorShapesCornerRadiusSlider.apply {
            value = arguments?.getFloat(BARCODE_IMAGE_CORNER_RADIUS_KEY, 0f) ?: 0f
            addOnChangeListener { _, value, _ ->
                onBarcodeDetailsActivity { activity ->
                    activity.regenerateBitmap(cornerRadius = value)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(cornerRadius: Float) = BarcodeImageEditorShapesFragment().apply {
            arguments = Bundle().apply {
                putFloat(BARCODE_IMAGE_CORNER_RADIUS_KEY, cornerRadius)
            }
        }
    }
}