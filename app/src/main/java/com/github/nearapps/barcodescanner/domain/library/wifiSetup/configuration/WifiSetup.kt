package com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration

import com.github.nearapps.barcodescanner.domain.library.wifiSetup.data.WifiSetupData
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.extensions.toEapMethod
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.extensions.toPhase2Method
import java.util.Locale

interface WifiSetup<CONFIGURATION> {

    fun configure(data: WifiSetupData): CONFIGURATION? {

        return when (data.authType.uppercase(Locale.getDefault())){
            "", "NOPASS" -> configureOpenNetwork(data.name, data.isHidden)
            "WPA", "WPA2" -> configureWpa2Network(data.name, data.password, data.isHidden)
            "WPA2-EAP" -> configureWpa2EapNetwork(data.name, data.password, data.isHidden, data.anonymousIdentity, data.identity, data.eapMethod.toEapMethod(), data.phase2Method.toPhase2Method())
            "WPA3", "SAE" -> configureWpa3Network(data.name, data.password, data.isHidden)
            "WPA3-EAP" -> configureWpa3EapNetwork(data.name, data.password, data.isHidden, data.anonymousIdentity, data.identity, data.eapMethod.toEapMethod(), data.phase2Method.toPhase2Method())
            "WEP" -> configureWepNetwork(data.name, data.password, data.isHidden)
            else -> null
        }
    }

    fun configureOpenNetwork(name: String, isHidden: Boolean): CONFIGURATION?

    fun configureWpa2Network(name: String, password: String, isHidden: Boolean): CONFIGURATION?

    fun configureWpa2EapNetwork(name: String,
                                password: String,
                                isHidden: Boolean,
                                anonymousIdentity: String,
                                identity: String,
                                eapMethod: Int?,
                                phase2Method: Int?): CONFIGURATION?

    fun configureWpa3Network(name: String, password: String, isHidden: Boolean): CONFIGURATION?

    fun configureWpa3EapNetwork(name: String,
                                password: String,
                                isHidden: Boolean,
                                anonymousIdentity: String,
                                identity: String,
                                eapMethod: Int?,
                                phase2Method: Int?): CONFIGURATION?

    fun configureWepNetwork(name: String,
                            password: String,
                            isHidden: Boolean): CONFIGURATION?
}