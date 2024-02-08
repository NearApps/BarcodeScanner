package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisLabelsBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.viewmodel.ExternalFileViewModel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.images.ImageAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisLabelsFragment : BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private val viewModel: ExternalFileViewModel by activityViewModel()

    private val uriList = mutableListOf<String>()
    private val imageAdapter: ImageAdapter by lazy {
        ImageAdapter(uriList)
    }

    private var _binding: FragmentFoodAnalysisLabelsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodAnalysisLabelsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        if(analysis.labels.isNullOrBlank() && analysis.labelsTagList.isNullOrEmpty()){
            viewBinding.root.visibility = View.GONE
        }else {
            viewBinding.root.visibility = View.VISIBLE
            viewBinding.fragmentFoodAnalysisLabelsTitleTextView.text = getString(R.string.labels_label)
            viewBinding.fragmentFoodAnalysisLabelsContentsTextView.text = analysis.labels
            configureRecyclerView()
            observeLabels(analysis)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeLabels(foodProduct: FoodBarcodeAnalysis){

        val labelsTags = foodProduct.labelsTagList

        if(!labelsTags.isNullOrEmpty()) {

            viewModel.obtainLabelsList(labelsTags).observe(viewLifecycleOwner) {

                uriList.clear()
                for (label in it) {
                    if (label.imageUrl != null) {
                        uriList.add(label.imageUrl)
                    }
                }

                imageAdapter.notifyDataSetChanged()

                val recyclerViewLayout =
                    viewBinding.fragmentFoodAnalysisLabelsImageRecyclerViewLayout

                if (uriList.isEmpty())
                    recyclerViewLayout.visibility = View.GONE
                else
                    recyclerViewLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun configureRecyclerView(){
        viewBinding.fragmentFoodAnalysisLabelsImageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }
}