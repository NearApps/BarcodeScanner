package com.github.nearapps.barcodescanner.domain.library.wifiSetup

import android.content.Intent
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration.WifiSetupWithNewLibrary
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration.WifiSetupWithOldLibrary
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.data.WifiSetupData
import com.github.nearapps.barcodescanner.presentation.intent.createWifiAddNetworksIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class WifiConnect: KoinComponent {

    @RequiresApi(Build.VERSION_CODES.R)
    fun connectWithApiR(data: WifiSetupData, previewRequest: ActivityResultLauncher<Intent>){
        val conf: WifiNetworkSuggestion? = get<WifiSetupWithNewLibrary>().configure(data)
        if(conf!=null) {

            val bundle = get<Bundle>().apply {
                putParcelableArrayList(Settings.EXTRA_WIFI_NETWORK_LIST, arrayListOf(conf))
            }

            val intent: Intent = createWifiAddNetworksIntent().apply {
                putExtras(bundle)
            }

            previewRequest.launch(intent)
        }
    }

    // ---- API 29 ----

    @RequiresApi(Build.VERSION_CODES.Q)
    inline fun connectWithApiQ(data: WifiSetupData, onResponse: (stringRes: Int) -> Unit){
        val conf: WifiNetworkSuggestion? = get<WifiSetupWithNewLibrary>().configure(data)
        val stringRes: Int = if(conf!=null) {
            val wifiManager: WifiManager = get()
            val response = wifiManager.addNetworkSuggestions(listOf(conf))

            when(response){
                WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS -> R.string.action_wifi_add_network_successful
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_INTERNAL -> R.string.action_wifi_add_network_failed
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_APP_DISALLOWED -> R.string.action_wifi_add_network_refused
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_DUPLICATE -> R.string.action_wifi_add_network_already_exists
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_EXCEEDS_MAX_PER_APP -> R.string.action_wifi_add_network_failed
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_REMOVE_INVALID -> R.string.action_wifi_add_network_failed
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_NOT_ALLOWED -> R.string.action_wifi_add_network_refused
                WifiManager.STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_INVALID -> R.string.action_wifi_add_network_failed
                else -> R.string.action_wifi_add_network_unknown_error
            }

        } else -1

        if(stringRes!=-1)
            onResponse(stringRes)
    }

    // ---- API 28 and less ----
    @Suppress("DEPRECATION")
    inline fun connectWithApiOld(data: WifiSetupData, onResponse: (stringRes: Int) -> Unit){
        val wifiManager: WifiManager = get()
        val stringRes: Int = if(wifiManager.isWifiEnabled){

            val conf: android.net.wifi.WifiConfiguration? = get<WifiSetupWithOldLibrary>().configure(data)
            if(conf!=null) {
                if (!wifiManager.isWifiEnabled)
                    wifiManager.isWifiEnabled = true
                wifiManager.disconnect()
                wifiManager.enableNetwork(wifiManager.addNetwork(conf), true)

                if(wifiManager.reconnect())
                    R.string.action_wifi_add_network_successful
                else
                    R.string.action_wifi_add_network_failed
            }else -1
        }else{
            R.string.action_wifi_connection_error_wifi_not_activated
        }
        if(stringRes!=-1)
            onResponse(stringRes)
    }
}