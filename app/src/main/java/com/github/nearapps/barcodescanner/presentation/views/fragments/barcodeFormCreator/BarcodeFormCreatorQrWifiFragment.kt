package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrWifiBinding

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrWifiFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrWifiBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrWifiBinding.inflate(inflater, container, false)
        configureMenu()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureWifiEncryptionAutoCompleteTextView()
    }

    override fun getBarcodeTextFromForm(): String {
        val ssid = viewBinding.fragmentBarcodeFormCreatorQrWifiSsidInputEditText.text.toString()
        val password = viewBinding.fragmentBarcodeFormCreatorQrWifiPasswordInputEditText.text.toString()
        val encryption = getEncryption()
        val hide = viewBinding.fragmentBarcodeFormCreatorQrWifiHideCheckBox.isChecked

        return "WIFI:T:$encryption;S:$ssid;P:$password;H:$hide;"
    }

    private fun configureWifiEncryptionAutoCompleteTextView(){
        val spinnerArray = arrayOf(
            getString(R.string.spinner_wifi_encryption_wep),
            getString(R.string.spinner_wifi_encryption_wpa),
            getString(R.string.spinner_wifi_encryption_sae),
            getString(R.string.spinner_wifi_encryption_none)
        )

        val spinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.template_spinner_item, spinnerArray)
        spinnerAdapter.setDropDownViewResource(R.layout.template_spinner_item)
        viewBinding.fragmentBarcodeFormCreatorQrWifiEncryptionAutoCompleteTextView.setAdapter(spinnerAdapter)
        viewBinding.fragmentBarcodeFormCreatorQrWifiEncryptionAutoCompleteTextView.setText(spinnerAdapter.getItem(0), false)
    }

    private fun getEncryption(): String = when(viewBinding.fragmentBarcodeFormCreatorQrWifiEncryptionAutoCompleteTextView.text.toString()) {
        getString(R.string.spinner_wifi_encryption_wep) -> "WEP"
        getString(R.string.spinner_wifi_encryption_wpa) -> "WPA"
        getString(R.string.spinner_wifi_encryption_sae) -> "SAE"
        getString(R.string.spinner_wifi_encryption_none) -> "nopass"
        else -> "WEP"
    }
}