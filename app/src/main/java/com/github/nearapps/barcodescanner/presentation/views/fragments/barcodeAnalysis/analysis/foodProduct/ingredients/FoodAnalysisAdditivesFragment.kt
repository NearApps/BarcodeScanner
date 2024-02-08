package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.ingredients

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentFoodAnalysisAdditivesBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.presentation.intent.createSearchUrlIntent
import com.github.nearapps.barcodescanner.presentation.viewmodel.ExternalFileViewModel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.BarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.additives.AdditivesItemAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * A simple [Fragment] subclass.
 */
class FoodAnalysisAdditivesFragment: BarcodeAnalysisFragment<FoodBarcodeAnalysis>() {

    private val viewModel: ExternalFileViewModel by activityViewModel()
    private var additivesAdapter: AdditivesItemAdapter? = null
    private var alertDialog: AlertDialog? = null

    private var _binding: FragmentFoodAnalysisAdditivesBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=FragmentFoodAnalysisAdditivesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
    }

    override fun start(analysis: FoodBarcodeAnalysis) {
        val additivesTagsList = analysis.additivesTagsList
        if(!additivesTagsList.isNullOrEmpty()) {
            configureEntitledView()
            configureRecyclerView()
            observeAdditives(additivesTagsList)
        } else {
            viewBinding.root.visibility = View.GONE
        }
    }

    private fun observeAdditives(additivesTagsList: List<String>) {
        viewModel.obtainAdditivesList(additivesTagsList).observe(viewLifecycleOwner) {
            additivesAdapter?.update(it)
            viewBinding.fragmentFoodAnalysisAdditivesProgressBar.visibility = View.GONE
            viewBinding.fragmentFoodAnalysisAdditivesCardView.visibility = View.VISIBLE
        }
    }

    private fun configureEntitledView() {
        viewBinding.fragmentFoodAnalysisAdditivesTitleTextView.text = getString(R.string.additives_label)
    }

    private fun configureRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        additivesAdapter = AdditivesItemAdapter(showAdditiveInfoDialog, searchAdditiveOnTheWeb)

        viewBinding.fragmentFoodAnalysisAdditivesRecyclerView.apply {
            adapter = additivesAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
            suppressLayout(true)
        }
    }

    private val showAdditiveInfoDialog = { additiveName: String, description: String ->
        alertDialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle(additiveName)
            setMessage(description)
            setNegativeButton(R.string.close_dialog_label) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
        }.show()
    }

    private val searchAdditiveOnTheWeb = { additiveId: String ->
        val url = requireActivity().getString(R.string.search_engine_additive_url, additiveId)
        val intent: Intent = createSearchUrlIntent(url)
        requireActivity().startActivity(intent)
    }
}
