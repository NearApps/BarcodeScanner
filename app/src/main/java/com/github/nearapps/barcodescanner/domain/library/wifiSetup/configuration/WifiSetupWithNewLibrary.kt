package com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration

import android.net.wifi.WifiEnterpriseConfig
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
open class WifiSetupWithNewLibrary: WifiSetup<WifiNetworkSuggestion> {

    override fun configureOpenNetwork(
        name: String,
        isHidden: Boolean
    ): WifiNetworkSuggestion = WifiNetworkSuggestion.Builder().apply {
        setSsid(name)
        setIsHiddenSsid(isHidden)
    }.build()

    override fun configureWpa2Network(
        name: String,
        password: String,
        isHidden: Boolean
    ): WifiNetworkSuggestion = WifiNetworkSuggestion.Builder().apply {
        setSsid(name)
        setWpa2Passphrase(password)
        setIsHiddenSsid(isHidden)
    }.build()

    override fun configureWpa2EapNetwork(
        name: String,
        password: String,
        isHidden: Boolean,
        anonymousIdentity: String,
        identity: String,
        eapMethod: Int?,
        phase2Method: Int?
    ): WifiNetworkSuggestion = WifiNetworkSuggestion.Builder().apply {
        setSsid(name)
        setWpa2Passphrase(password)
        setIsHiddenSsid(isHidden)
        setWpa2EnterpriseConfig(
            getWifiEnterpriseConfig(password, anonymousIdentity, identity, eapMethod, phase2Method)
        )
    }.build()

    override fun configureWpa3Network(
        name: String,
        password: String,
        isHidden: Boolean
    ): WifiNetworkSuggestion = WifiNetworkSuggestion.Builder().apply {
        setSsid(name)
        setWpa3Passphrase(password)
        setIsHiddenSsid(isHidden)
    }.build()

    override fun configureWpa3EapNetwork(
        name: String,
        password: String,
        isHidden: Boolean,
        anonymousIdentity: String,
        identity: String,
        eapMethod: Int?,
        phase2Method: Int?
    ): WifiNetworkSuggestion = WifiNetworkSuggestion.Builder().apply {
        setSsid(name)
        setWpa3Passphrase(password)
        setIsHiddenSsid(isHidden)

        val wifiEnterpriseConfig = getWifiEnterpriseConfig(password, anonymousIdentity, identity, eapMethod, phase2Method)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setWpa3EnterpriseStandardModeConfig(wifiEnterpriseConfig)
        }else {
            @Suppress("DEPRECATION")
            setWpa3EnterpriseConfig(wifiEnterpriseConfig)
        }
    }.build()

    override fun configureWepNetwork(
        name: String,
        password: String,
        isHidden: Boolean
    ): WifiNetworkSuggestion? = null

    private fun getWifiEnterpriseConfig(
        password: String,
        anonymousIdentity: String,
        identity: String,
        eapMethod: Int?,
        phase2Method: Int?
    ) = WifiEnterpriseConfig().also { config ->

        config.anonymousIdentity = anonymousIdentity
        config.identity = identity
        config.password = password

        eapMethod?.apply {
            config.eapMethod = this
        }

        phase2Method?.apply {
            config.phase2Method = this
        }
    }
}