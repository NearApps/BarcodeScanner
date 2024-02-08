package com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.FragmentBarcodeFormCreatorQrLocalisationBinding
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class BarcodeFormCreatorQrLocalisationFragment : AbstractBarcodeFormCreatorQrFragment() {

    private var _binding: FragmentBarcodeFormCreatorQrLocalisationBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarcodeFormCreatorQrLocalisationBinding.inflate(inflater, container, false)
        configureMenu()
        updateSearchViews(false)
        configureFindLocalisation()
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopLocation()
        _binding=null
    }

    override fun onPause() {
        super.onPause()
        stopLocation()
    }

    override fun getBarcodeTextFromForm(): String {

        val latitude = viewBinding.fragmentBarcodeFormCreatorQrLocalisationLatitudeInputEditText.text.toString()
        val longitude = viewBinding.fragmentBarcodeFormCreatorQrLocalisationLongitudeInputEditText.text.toString()
        val request = viewBinding.fragmentBarcodeFormCreatorQrLocalisationRequestInputEditText.text.toString()

        return when {
            latitude.isNotBlank() && longitude.isNotBlank() && request.isNotBlank() -> "geo:$latitude,$longitude?q=$request"
            latitude.isNotBlank() && longitude.isNotBlank() && request.isBlank() -> "geo:$latitude,$longitude"
            else -> ""
        }
    }

    override val checkError: (contents: String) -> String? by lazy {
        { barcodeFormatChecker.checkQrLocalisationError(it) }
    }

    // ---- Permissions ----

    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    /**
     * Gère le resultat de la demande de permission d'accès à la localisation.
     */
    private val requestPermission: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) findLocalisation() else stopLocation()
        }


    private fun configureFindLocalisation() {
        viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                findLocalisation()
            } else {
                requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        viewBinding.fragmentBarcodeFormCreatorQrLocalisationCancelButton.setOnClickListener {
            stopLocation()
        }
    }

    private fun findLocalisation() {
        updateSearchViews(true)

        val locationListener = object : LocationListenerCompat {
            override fun onLocationChanged(location: Location) {
                viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchInfoLayout.visibility = View.GONE
                viewBinding.fragmentBarcodeFormCreatorQrLocalisationLatitudeInputEditText.setText(location.latitude.toString())
                viewBinding.fragmentBarcodeFormCreatorQrLocalisationLongitudeInputEditText.setText(location.longitude.toString())
            }

            override fun onProviderEnabled(provider: String) {
                super.onProviderEnabled(provider)
                hideErrorMessage()
                viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchInfoLayout.visibility = View.VISIBLE
            }

            override fun onProviderDisabled(provider: String) {
                super.onProviderDisabled(provider)
                configureErrorMessage(getString(R.string.matrix_localisation_location_disabled_label))
                viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchInfoLayout.visibility = View.GONE
            }
        }

        val locationManager: LocationManager = get()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

        this.locationManager = locationManager
        this.locationListener = locationListener
    }

    private fun stopLocation() {
        locationListener?.let {
            locationManager?.removeUpdates(it)
        }
        locationManager = null
        locationListener = null

        updateSearchViews(false)
        hideErrorMessage()
    }

    private fun updateSearchViews(isSearching: Boolean) {
        if(isSearching){
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchButton.visibility = View.GONE
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationCancelButton.visibility = View.VISIBLE
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchInfoLayout.visibility = View.VISIBLE
        } else {
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchButton.visibility = View.VISIBLE
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationCancelButton.visibility = View.GONE
            viewBinding.fragmentBarcodeFormCreatorQrLocalisationSearchInfoLayout.visibility = View.GONE
        }
    }
}