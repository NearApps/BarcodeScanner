package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.ActivityBarcodeScanOnlyBinding
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainCameraXScannerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainScannerFragment
import org.koin.android.ext.android.inject

class BarcodeScanOnlyActivity : BaseActivity() {

    private val mainCameraXScannerFragment: MainCameraXScannerFragment by inject()
    private val mainScannerFragment: MainScannerFragment by inject()

    private val viewBinding: ActivityBarcodeScanOnlyBinding by lazy { ActivityBarcodeScanOnlyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityMainToolbar.toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)// On n'affiche pas l'icone "retour" dans la MainActivity
            it.setTitle(R.string.title_scan)
        }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(viewBinding.activityBarcodeScanOnlyFrameLayout.id, if(settingsManager.useCameraXApi) mainCameraXScannerFragment else mainScannerFragment)
            //addToBackStack(null) // Permet de revenir aux fragments affichés précédement via le bouton back
        }

        setContentView(viewBinding.root)
    }
}