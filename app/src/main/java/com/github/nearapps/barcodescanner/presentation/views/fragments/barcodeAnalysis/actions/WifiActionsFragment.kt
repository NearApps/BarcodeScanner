package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.WifiConnect
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.data.WifiSetupData
import com.github.nearapps.barcodescanner.presentation.intent.createPickWifiNetworkIntent
import com.github.nearapps.barcodescanner.presentation.views.recyclerView.actionButton.ActionItem
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.WifiParsedResult

class WifiActionsFragment: AbstractParsedResultActionsFragment() {

    override fun configureActions(barcode: Barcode, parsedResult: ParsedResult): Array<ActionItem> {
        return when(parsedResult){
            is WifiParsedResult -> configureWifiActions(barcode, parsedResult)
            else -> configureDefaultActions(barcode)
        }
    }

    private fun configureWifiActions(barcode: Barcode, parsedResult: WifiParsedResult) = arrayOf(
        ActionItem(R.string.qr_code_type_name_wifi, R.drawable.baseline_wifi_24, showWifiAlertDialog(parsedResult))
    ) + configureDefaultActions(barcode)

    // Actions

    private fun showWifiAlertDialog(parsedResult: WifiParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val items = arrayOf<Pair<String, ActionItem.OnActionItemListener>>(
                Pair(getString(R.string.action_wifi_connection_from_app), connectToWifiFromApp(parsedResult)),
                Pair(getString(R.string.action_wifi_connection_from_wifi_settings), connectToWifiFromWifiSettings(parsedResult))
            )

            createAlertDialog(requireContext(), getString(R.string.qr_code_type_name_wifi), items).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private val wifiPreviewRequest: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){

                val data = result.data
                if (data?.hasExtra(Settings.EXTRA_WIFI_NETWORK_RESULT_LIST) == true) {

                    val wifiNetworkResultList: IntArray? =
                        data.getIntegerArrayListExtra(Settings.EXTRA_WIFI_NETWORK_RESULT_LIST)?.toIntArray()

                    wifiNetworkResultList?.forEach { code ->

                        val message = when (code) {
                            Settings.ADD_WIFI_RESULT_SUCCESS -> getString(R.string.action_wifi_add_network_successful)
                            Settings.ADD_WIFI_RESULT_ADD_OR_UPDATE_FAILED -> getString(R.string.action_wifi_add_network_failed)
                            Settings.ADD_WIFI_RESULT_ALREADY_EXISTS -> getString(R.string.action_wifi_add_network_already_exists)
                            else -> getString(R.string.action_wifi_add_network_unknown_error)
                        }
                        showSnackbar(message)
                    }
                }
            }else{
                showSnackbar(getString(R.string.action_wifi_add_network_refused))
            }
        }

    private fun connectToWifiFromApp(parsedResult: WifiParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            val data: WifiSetupData = configureWifiSetupData(parsedResult)
            val wifiConnect = WifiConnect()

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> wifiConnect.connectWithApiR(data, wifiPreviewRequest)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> wifiConnect.connectWithApiQ(data) {
                    showSnackbar(getString(it))
                }
                else -> wifiConnect.connectWithApiOld(data) {
                    showSnackbar(getString(it))
                }
            }
        }
    }

    /**
     * Copie le mot de passe dans le presse papier et ouvre les paramètres Wifi.
     */
    private fun connectToWifiFromWifiSettings(parsedResult: WifiParsedResult): ActionItem.OnActionItemListener = object : ActionItem.OnActionItemListener {
        override fun onItemClick(view: View?) {
            copyToClipboard("password", parsedResult.password)
            showToastText(R.string.action_wifi_password_copy_label)

            val intent: Intent = createPickWifiNetworkIntent()
            startActivity(intent)
        }
    }

    private fun configureWifiSetupData(parsedResult: WifiParsedResult): WifiSetupData {
        return WifiSetupData(
            authType = parsedResult.networkEncryption ?: "",
            name = parsedResult.ssid ?: "",
            password = parsedResult.password ?: "",
            isHidden = parsedResult.isHidden,
            anonymousIdentity = "",
            identity = "",
            eapMethod = "",
            phase2Method = ""
        )
    }
}