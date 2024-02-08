package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisQualityBinding
import com.github.nearapps.barcodescanner.presentation.views.fragments.BaseFragment
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisQualityFragment : BaseFragment() {

    private var _binding: FragmentFoodAnalysisQualityBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisQualityBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews(){
        arguments?.let {
            val title = it.getString(TITLE_KEY)
            val subtitle = it.getString(SUBTITLE_KEY)
            val description = it.getString(DESCRIPTION_KEY)
            val drawableResource = it.getInt(DRAWABLE_RESOURCE_KEY)

            if (!title.isNullOrBlank() || !subtitle.isNullOrBlank() || !description.isNullOrBlank() || drawableResource!=-1) {
                viewBinding.root.visibility = View.VISIBLE

                viewBinding.fragmentFoodProductQualityTitleTextView.text = title
                viewBinding.fragmentFoodProductQualitySubtitleTextView.text = subtitle
                viewBinding.fragmentFoodProductQualityImageView.setImageResource(drawableResource)
                viewBinding.fragmentFoodProductQualityDescriptionTextView.text = description
            } else {
                viewBinding.root.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val DRAWABLE_RESOURCE_KEY = "drawableResourceKey"
        private const val TITLE_KEY = "titleKey"
        private const val SUBTITLE_KEY = "subtitleKey"
        private const val DESCRIPTION_KEY = "descriptionKey"

        @JvmStatic
        fun newInstance(drawableRes: Int, title: String, subtitle: String, description: String? = null) =
            FoodAnalysisQualityFragment().apply {
                arguments = get<Bundle>().apply {
                    putString(TITLE_KEY, title)
                    putString(SUBTITLE_KEY, subtitle)
                    putInt(DRAWABLE_RESOURCE_KEY, drawableRes)
                    if(description != null)
                        putString(DESCRIPTION_KEY, description)
                }
            }
    }
}