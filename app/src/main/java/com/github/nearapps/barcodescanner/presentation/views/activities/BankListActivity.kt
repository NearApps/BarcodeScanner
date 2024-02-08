package com.github.nearapps.barcodescanner.presentation.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.utils.BANK_KEY
import com.github.nearapps.barcodescanner.databinding.ActivityBankListBinding
import com.github.nearapps.barcodescanner.domain.entity.bank.Bank
import com.github.nearapps.barcodescanner.presentation.customView.CustomItemTouchHelperCallback
import com.github.nearapps.barcodescanner.presentation.customView.MarginItemDecoration
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBankViewModel
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory.BankHistoryItemAdapter
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.bankHistory.BankHistoryItemTouchHelperListener
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Liste les données de banque pour simplifier la génération de codes EPC.
 * Est ouverte via un onActivityResult dans le fragment BarcodeFormCreatorQrEpcFragment.
 * Lorsqu'on click sur un item, cela renvoie les informations de Bank via un Intent dans le Fragment appelant.
 */
class BankListActivity : BaseActivity(), BankHistoryItemAdapter.OnBankItemListener, BankHistoryItemTouchHelperListener {

    private val viewBinding: ActivityBankListBinding by lazy {
        ActivityBankListBinding.inflate(layoutInflater)
    }

    private val databaseBankViewModel by viewModel<DatabaseBankViewModel>()

    private val adapter: BankHistoryItemAdapter = BankHistoryItemAdapter(this)
    private val bankItemSelected by lazy { mutableListOf<Bank>() }
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.activityBankListEmptyTextView.visibility = View.GONE
        viewBinding.activityBankListHistoryRecyclerView.visibility = View.GONE

        setSupportActionBar(viewBinding.activityBankListToolbar.toolbar)
        configureRecyclerView()
        observeDatabase()

        setContentView(viewBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        menu.removeItem(R.id.menu_history_export)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_history_delete_all -> {
                if(bankItemSelected.isEmpty())
                    showDeleteAllConfirmationDialog()
                else
                    showDeleteSelectedItemsConfirmationDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {
        val recyclerView = viewBinding.activityBankListHistoryRecyclerView

        val layoutManager = LinearLayoutManager(this)
        val decoration = MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.standard_margin))

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(decoration)

        val itemTouchHelperCallback =
            CustomItemTouchHelperCallback(
                this,
                0,
                ItemTouchHelper.START// support Rtl
            )
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun observeDatabase() {
        databaseBankViewModel.bankList.observe(this) {
            bankItemSelected.clear()
            adapter.updateData(it)

            if (it.isEmpty()) {
                viewBinding.activityBankListEmptyTextView.visibility = View.VISIBLE
                viewBinding.activityBankListHistoryRecyclerView.visibility = View.GONE
            } else {
                viewBinding.activityBankListEmptyTextView.visibility = View.GONE
                viewBinding.activityBankListHistoryRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    // ---- Item Actions ----

    override fun onItemClick(view: View?, bank: Bank) {
        val intent = Intent().apply {
            putExtra(BANK_KEY, bank)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onItemSelect(view: View?, bank: Bank, isSelected: Boolean) {
        if(isSelected){
            bankItemSelected.add(bank)
        }else{
            bankItemSelected.remove(bank)
        }
    }

    override fun isSelectedMode(): Boolean = bankItemSelected.isNotEmpty()

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val bank: Bank = adapter.getBank(position)
        databaseBankViewModel.deleteBank(bank)
        Snackbar.make(
            viewBinding.root,
            getString(R.string.menu_item_history_removed_from_history),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    // ---- Delete History From Menu ----

    private fun showDeleteAllConfirmationDialog() {
        showDeleteConfirmationDialog(R.string.popup_message_confirmation_delete_history) {
            databaseBankViewModel.deleteAll()
        }
    }

    private fun showDeleteSelectedItemsConfirmationDialog() {
        showDeleteConfirmationDialog(R.string.popup_message_confirmation_delete_selected_items_history) {
            databaseBankViewModel.deleteBanks(bankItemSelected)
        }
    }

    private inline fun showDeleteConfirmationDialog(messageRes: Int, crossinline positiveAction: () -> Unit) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.delete_label)
            .setMessage(messageRes)
            .setPositiveButton(R.string.delete_label) { _, _ ->
                positiveAction()
            }
            .setNegativeButton(R.string.cancel_label, null)
            .show()
    }
}