package com.github.nearapps.barcodescanner.presentation.views.fragments.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.databinding.FragmentExpandableCardViewBinding
import org.koin.android.ext.android.get

class ExpandableCardViewFragment : Fragment() {

    private var title: String? = null
    private var contents: CharSequence? = null
    private var drawableResource: Int? = null

    private var _binding: FragmentExpandableCardViewBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ENTITLED_KEY)
            contents = it.getCharSequence(CONTENTS_KEY)
            drawableResource = it.getInt(ICON_KEY, DEFAULT_INT_VALUE_BUNDLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExpandableCardViewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureDrawableResource(drawableResource)
        viewBinding.fragmentExpandableCardViewTitleTextView.text = title
        viewBinding.fragmentExpandableCardViewContentsTextView.text = contents
    }

    private fun configureDrawableResource(drawableResource: Int?){
        viewBinding.fragmentExpandableCardViewIconImageView.apply {
            if (drawableResource != null && drawableResource != DEFAULT_INT_VALUE_BUNDLE) {
                setImageResource(drawableResource)
            } else {
                visibility = View.GONE
            }
        }
    }

    companion object {

        private const val DEFAULT_INT_VALUE_BUNDLE = -1
        private const val ENTITLED_KEY = "entitledKey"
        private const val CONTENTS_KEY = "contentsKey"
        private const val ICON_KEY = "iconKey"

        @JvmStatic
        fun newInstance(title: String, contents: CharSequence, drawableResource: Int? = null) =
            ExpandableCardViewFragment().apply {
                arguments = get<Bundle>().apply {
                    putString(ENTITLED_KEY, title)
                    putCharSequence(CONTENTS_KEY, contents)
                    if(drawableResource != null)
                        putInt(ICON_KEY, drawableResource)
                }
            }
    }
}