package com.github.nearapps.barcodescanner.presentation.views.activities

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commitNow
import com.github.nearapps.barcodescanner.BuildConfig
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.databinding.ActivityMainBinding
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainBarcodeCreatorListFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainBarcodeHistoryFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainCameraXScannerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainScannerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainSettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity() {

    companion object {
        private const val ITEM_ID_KEY = "itemIdKey"
        private const val TITLE_RES_KEY = "titleResId"
    }

    private var currentItemId: Int = R.id.menu_navigation_bottom_view_scan
    private var titleRes: Int = R.string.title_scan

    private val mainCameraXScannerFragment: MainCameraXScannerFragment by inject()
    private val mainScannerFragment: MainScannerFragment by inject()
    private val mainHistoryFragment: MainBarcodeHistoryFragment by inject()
    private val mainBarcodeCreatorListFragment: MainBarcodeCreatorListFragment by inject()
    private val mainSettingsFragment: MainSettingsFragment by inject()

    private val viewBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            currentItemId = it.getInt(ITEM_ID_KEY, R.id.menu_navigation_bottom_view_scan)
            titleRes = it.getInt(TITLE_RES_KEY, R.string.title_scan)
        }

        setSupportActionBar(viewBinding.activityMainToolbar.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)// On n'affiche pas l'icone "retour" dans la MainActivity
            it.setTitle(titleRes)
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            configureNavigationRailView()
        } else {
            configureBottomNavigationMenu()
        }

        setContentView(viewBinding.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ITEM_ID_KEY, currentItemId)
        outState.putInt(TITLE_RES_KEY, titleRes)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // ---- Configuration ----

    /**
     * Permet de coloriser la bar de navigation (en bas) de la même couleur que la BottomNavigationView.
     */
    private fun configureNavigationBarColor(bottomNavigationView: BottomNavigationView) {
        if (Build.VERSION.SDK_INT >= 24 || settingsManager.useDarkTheme()) {
            val bottomNavigationViewBackground = bottomNavigationView.background
            if (bottomNavigationViewBackground is MaterialShapeDrawable) {
                this.window.navigationBarColor = bottomNavigationViewBackground.resolvedTintColor
            }
        }
    }

    private fun configureBottomNavigationMenu() {
        viewBinding.activityMainMenuBottomNavigation?.let { bottomNavigationView ->
            bottomNavigationView.selectedItemId = currentItemId
            configureNavigationBarColor(bottomNavigationView)
            bottomNavigationView.setOnItemSelectedListener {
                changeView(it.itemId)
            }
            initializeFirstFragmentToShow {
                bottomNavigationView.selectedItemId = it
            }
        }
    }

    private fun configureNavigationRailView() {
        viewBinding.activityMainNavigationRail?.let { navigationRailView ->
            navigationRailView.selectedItemId = currentItemId
            navigationRailView.setOnItemSelectedListener {
                changeView(it.itemId)
            }
            initializeFirstFragmentToShow {
                navigationRailView.selectedItemId = it
            }
        }
    }

    private fun initializeFirstFragmentToShow(onChangeItem: (selectedItemId: Int) -> Unit) {
        if(supportFragmentManager.fragments.isEmpty()) {
            // Utile lorsqu'on ouvre l'application via un shortcut
            val selectedItemId = when (intent?.action) {
                "${BuildConfig.APPLICATION_ID}.SCAN" -> R.id.menu_navigation_bottom_view_scan
                "${BuildConfig.APPLICATION_ID}.HISTORY" -> R.id.menu_navigation_bottom_view_history
                "${BuildConfig.APPLICATION_ID}.CREATE" -> R.id.menu_navigation_bottom_view_create
                "android.intent.action.APPLICATION_PREFERENCES" -> R.id.menu_navigation_bottom_view_settings
                else -> currentItemId
            }
            onChangeItem(selectedItemId)
            changeView(selectedItemId)
        }
    }

    // ---- Change Fragment ----

    private fun changeView(id: Int): Boolean {
        currentItemId = id
        return when (id) {
            R.id.menu_navigation_bottom_view_scan -> changeFragment(
                if (settingsManager.useCameraXApi) mainCameraXScannerFragment else mainScannerFragment,
                R.string.title_scan
            )

            R.id.menu_navigation_bottom_view_history -> changeFragment(
                mainHistoryFragment,
                R.string.title_history
            )

            R.id.menu_navigation_bottom_view_create -> changeFragment(
                mainBarcodeCreatorListFragment,
                R.string.title_bar_code_creator
            )

            R.id.menu_navigation_bottom_view_settings -> changeFragment(
                mainSettingsFragment,
                R.string.title_settings
            )

            else -> false
        }
    }

    private fun changeFragment(fragment: Fragment, titleResource: Int): Boolean {
        titleRes = titleResource
        supportActionBar?.setTitle(titleResource)
        supportFragmentManager.commitNow {
            setReorderingAllowed(false)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(viewBinding.activityMainFrameLayout.id, fragment)
            //addToBackStack(null) // Permet de revenir aux fragments affichés précédement via le bouton back
        }
        return true
    }

    // ---- UI ----

    fun showSnackbar(text: String) {
        val snackbar = Snackbar.make(viewBinding.root, text, Snackbar.LENGTH_SHORT)
        snackbar.anchorView = viewBinding.activityMainMenuBottomNavigation
        snackbar.show()
    }

    // ---- Theme ----

    fun updateTheme() {
        settingsManager.reload()
        recreate()
    }
}