@file:Suppress("DEPRECATION")

package com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration

import android.net.wifi.WifiConfiguration
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.extensions.quote
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.extensions.quoteIfNotHex

/**
 * Util: https://stackoverflow.com/questions/8818290/how-do-i-connect-to-a-specific-wi-fi-network-in-android-programmatically
 */
open class WifiSetupWithOldLibrary: WifiSetup<WifiConfiguration> {

    override fun configureOpenNetwork(
        name: String,
        isHidden: Boolean
    ): WifiConfiguration = getWifiConfiguration().apply {
        SSID = name.quote()
        hiddenSSID = isHidden
        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        allowedAuthAlgorithms.clear()
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
    }

    override fun configureWpa2Network(
        name: String,
        password: String,
        isHidden: Boolean
    ): WifiConfiguration = getWifiConfiguration().apply {
        SSID = name.quote()
        preSharedKey = password.quoteIfNotHex()
        hiddenSSID = isHidden
        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun configureWpa2EapNetwork(
        name: String,
        password: String,
        isHidden: Boolean,
        anonymousIdentity: String,
        identity: String,
        eapMethod: Int?,
        phase2Method: Int?
    ): WifiConfiguration = getWifiConfiguration().apply {
        SSID = name.quote()
        preSharedKey = password.quoteIfNotHex()
        hiddenSSID = isHidden

        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)

        enterpriseConfig.anonymousIdentity = anonymousIdentity
        enterpriseConfig.identity = identity
        enterpriseConfig.password = password

        eapMethod?.apply {
            enterpriseConfig.eapMethod = this
        }

        phase2Method?.apply {
            enterpriseConfig.phase2Method = this
        }
    }

    override fun configureWpa3Network(name: String, password: String, isHidden: Boolean): WifiConfiguration? = null

    override fun configureWpa3EapNetwork(name: String, password: String, isHidden: Boolean, anonymousIdentity: String, identity: String, eapMethod: Int?, phase2Method: Int?): WifiConfiguration? = null

    override fun configureWepNetwork(
        name: String,
        password: String,
        isHidden: Boolean
    ): WifiConfiguration = getWifiConfiguration().apply {
        SSID = name.quote()
        wepKeys[0] = password.quoteIfNotHex()
        hiddenSSID = isHidden
        wepTxKeyIndex = 0
        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
        allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
    }

    private fun getWifiConfiguration(): WifiConfiguration = WifiConfiguration().apply {
        allowedProtocols.set(WifiConfiguration.Protocol.RSN)
        allowedProtocols.set(WifiConfiguration.Protocol.WPA)
        allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
        allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
    }
}