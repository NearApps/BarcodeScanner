package com.github.nearapps.barcodescanner.domain.library.wifiSetup.extensions

import android.net.wifi.WifiEnterpriseConfig
import android.os.Build

internal fun String.toEapMethod(): Int? = when (this) {
    "AKA" -> WifiEnterpriseConfig.Eap.AKA
    "AKA_PRIME" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) WifiEnterpriseConfig.Eap.AKA_PRIME else null
    "NONE" -> WifiEnterpriseConfig.Eap.NONE
    "PEAP" -> WifiEnterpriseConfig.Eap.PEAP
    "PWD" -> WifiEnterpriseConfig.Eap.PWD
    "SIM" -> WifiEnterpriseConfig.Eap.SIM
    "TLS" -> WifiEnterpriseConfig.Eap.TLS
    "TTLS" -> WifiEnterpriseConfig.Eap.TTLS
    "UNAUTH_TLS" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) WifiEnterpriseConfig.Eap.UNAUTH_TLS else null
    else -> null
}

internal fun String.toPhase2Method(): Int =
    when (this) {
        "AKA" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WifiEnterpriseConfig.Phase2.AKA else WifiEnterpriseConfig.Phase2.NONE
        "AKA_PRIME" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WifiEnterpriseConfig.Phase2.AKA_PRIME else WifiEnterpriseConfig.Phase2.NONE
        "GTC" -> WifiEnterpriseConfig.Phase2.GTC
        "MSCHAP" -> WifiEnterpriseConfig.Phase2.MSCHAP
        "MSCHAPV2" -> WifiEnterpriseConfig.Phase2.MSCHAPV2
        "NONE" -> WifiEnterpriseConfig.Phase2.NONE
        "PAP" -> WifiEnterpriseConfig.Phase2.PAP
        "SIM" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WifiEnterpriseConfig.Phase2.SIM else WifiEnterpriseConfig.Phase2.NONE
        else -> WifiEnterpriseConfig.Phase2.NONE
    }

/**
 * Avec l'ancienne API de connexion WifiConfiguration, le SSID et le mot de passe doivent être entre guillemets.
 */
internal fun String.quote(): String {
    return if (startsWith("\"") && endsWith("\"")) this else "\"$this\""
}

private fun String.isHex(): Boolean {
    return length == 64 && matches("""^[0-9a-f]+$""".toRegex(RegexOption.IGNORE_CASE))
}

internal fun String.quoteIfNotHex(): String = if(isHex()) this else quote()