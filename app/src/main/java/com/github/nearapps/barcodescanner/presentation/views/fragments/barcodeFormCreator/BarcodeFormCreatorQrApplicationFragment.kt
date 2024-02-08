package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrApplicationBinding
import com.github.nearapps.barcodescanner.presentation.customView.MarginItemDecoration
import com.github.nearapps.barcodescanner.presentation.viewmodel.InstalledAppsViewModel
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications.ApplicationsItem
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.applications.ApplicationsItemAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BarcodeFormCreatorQrApplicationFragment : AbstractBarcodeFormCreatorQrFragment(), ApplicationsItemAdapter.OnApplicationItemListener {

    companion object {
        private const val PREFIX = "market://details?id="
    }

    private val viewModel by activityViewModel<InstalledAppsViewModel>()

    private var _binding: FragmentBarcodeFormCreatorQrApplicationBinding? = null
    private val viewBinding get() = _binding!!

    private val adapter = ApplicationsItemAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrApplicationBinding.inflate(inflater, container, false)

        /*viewBinding.fragmentBarcodeFormCreatorQrApplicationRecyclerView.visibility = View.GONE
        viewBinding.fragmentBarcodeFormCreatorQrApplicationProgressBar.visibility = View.VISIBLE*/

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        viewModel.installedApps.observe(viewLifecycleOwner) {
            viewBinding.fragmentBarcodeFormCreatorQrApplicationProgressBar.visibility = View.GONE
            adapter.updateData(it)
        }
    }

    private fun configureRecyclerView() {
        val recyclerView = viewBinding.fragmentBarcodeFormCreatorQrApplicationRecyclerView

        val layoutManager = LinearLayoutManager(requireContext())
        val decoration = MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.standard_margin))

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(decoration)

        //recyclerView.visibility = View.VISIBLE
    }

    private var barcodeContents = ""
    override fun getBarcodeTextFromForm(): String = barcodeContents

    override fun onItemClick(view: View?, item: ApplicationsItem) {
        barcodeContents = "${PREFIX}${item.pkg}"
        generateBarcode()
    }
}