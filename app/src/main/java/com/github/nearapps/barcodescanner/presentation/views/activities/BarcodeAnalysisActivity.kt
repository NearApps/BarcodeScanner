package com.github.nearapps.barcodescanner.presentation.views.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.convertToString
import com.github.nearapps.barcodescanner.common.extensions.serializable
import com.github.nearapps.barcodescanner.common.utils.BARCODE_KEY
import com.github.nearapps.barcodescanner.databinding.ActivityBarcodeAnalysisBinding
import com.github.nearapps.barcodescanner.domain.entity.analysis.BarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.BookBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.DefaultBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.FoodBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.MusicBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPI
import com.github.nearapps.barcodescanner.domain.entity.analysis.RemoteAPIError
import com.github.nearapps.barcodescanner.domain.entity.analysis.UnknownProductBarcodeAnalysis
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.domain.resources.Resource
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.ProductViewModel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.bookProduct.BookAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.defaultAnalysis.DefaultBarcodeAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.foodProduct.FoodAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.musicProduct.MusicAnalysisFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.analysis.unknownProduct.ProductAnalysisFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BarcodeAnalysisActivity: BaseActivity() {

    // ---- Views ----

    private val viewBinding: ActivityBarcodeAnalysisBinding by lazy {
        ActivityBarcodeAnalysisBinding.inflate(layoutInflater)
    }

    // ---- ViewModel ----

    private val databaseBarcodeViewModel by viewModel<DatabaseBarcodeViewModel>()
    private val retrofitViewModel by viewModel<ProductViewModel>()

    // ----

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityBarcodeInformationToolbar.toolbar)

        intent?.serializable(BARCODE_KEY, Barcode::class.java)?.let { barcode: Barcode ->
            configureContentsView(barcode)
        }

        setContentView(viewBinding.root)
    }

    private fun configureContentsView(barcode: Barcode) {
        changeToolbarText(barcode)
        when {
            barcode.is1DProductBarcodeFormat -> configureProductRemoteAPI(barcode)
            else -> configureDefaultBarcodeAnalysisView(DefaultBarcodeAnalysis(barcode))
        }
    }

    private fun configureProductRemoteAPI(barcode: Barcode) {

        // Check Internet Permission
        if(!isInternetPermissionGranted()) {
            configureUnknownProductAnalysisView(
                barcodeAnalysis = UnknownProductBarcodeAnalysis(
                    barcode = barcode,
                    apiError = RemoteAPIError.NO_INTERNET_PERMISSION,
                    message = getString(R.string.no_internet_permission)
                )
            )
            return
        }

        observeProductFromAPI()
        if(settingsManager.useSearchOnApi) {
            fetchProductFromRemoteAPI(barcode, determineAPIRemote(barcode.getBarcodeType()))
        } else {
            fetchProductFromRemoteAPI(barcode, RemoteAPI.NONE)
        }
    }

    // ---- Query remote API ----
    private fun observeProductFromAPI() {
        retrofitViewModel.product.observe(this) {

            when (it) {

                is Resource.Progress -> {
                    viewBinding.activityBarcodeInformationProgressBar.visibility = View.VISIBLE
                }

                is Resource.Failure -> {
                    viewBinding.activityBarcodeInformationProgressBar.visibility = View.GONE
                    configureUnknownProductAnalysisView(
                        barcodeAnalysis = it.data as? UnknownProductBarcodeAnalysis
                            ?: UnknownProductBarcodeAnalysis(
                                barcode = it.data.barcode,
                                apiError = RemoteAPIError.ERROR,
                                message = it.throwable.toString(),
                                source = it.data.source
                            ),
                    )
                }

                is Resource.Success -> {
                    viewBinding.activityBarcodeInformationProgressBar.visibility = View.GONE
                    when (it.data) {
                        is FoodBarcodeAnalysis -> configureFoodAnalysisView(it.data)
                        is MusicBarcodeAnalysis -> configureMusicAnalysisView(it.data)
                        is BookBarcodeAnalysis -> configureBookAnalysisView(it.data)
                        is UnknownProductBarcodeAnalysis -> configureUnknownProductAnalysisView(it.data)
                        else -> configureUnknownProductAnalysisView(
                            barcodeAnalysis = UnknownProductBarcodeAnalysis(
                                barcode = it.data.barcode,
                                apiError = RemoteAPIError.NO_RESULT,
                                source = it.data.source
                            )
                        )
                    }
                }

                else -> {
                    viewBinding.activityBarcodeInformationProgressBar.visibility = View.GONE
                }
            }
        }
    }

    fun fetchProductFromRemoteAPI(barcode: Barcode, apiRemote: RemoteAPI? = null) {
        if(isInternetPermissionGranted()) {
            retrofitViewModel.fetchProduct(
                barcode = barcode,
                apiRemote = apiRemote ?: determineAPIRemote(barcode.getBarcodeType())
            )
        }
    }

    // ---- Configuration de la vue principale en fonction du type de code-barres / produits ----

    private fun configureFoodAnalysisView(
        barcodeAnalysis: FoodBarcodeAnalysis
    ) {
        updateTypeIntoDatabase(barcodeAnalysis = barcodeAnalysis)
        configureContentFragment(
            fragment = FoodAnalysisFragment.newInstance(barcodeAnalysis),
            barcodeAnalysis = barcodeAnalysis
        )
    }

    private fun configureMusicAnalysisView(
        barcodeAnalysis: MusicBarcodeAnalysis
    ) {
        updateTypeIntoDatabase(barcodeAnalysis = barcodeAnalysis)
        configureContentFragment(
            fragment = MusicAnalysisFragment.newInstance(barcodeAnalysis),
            barcodeAnalysis = barcodeAnalysis
        )
    }

    private fun configureBookAnalysisView(
        barcodeAnalysis: BookBarcodeAnalysis
    ) {
        updateTypeIntoDatabase(barcodeAnalysis = barcodeAnalysis)
        configureContentFragment(
            fragment = BookAnalysisFragment.newInstance(barcodeAnalysis),
            barcodeAnalysis = barcodeAnalysis
        )
    }

    private fun configureUnknownProductAnalysisView(
        barcodeAnalysis: UnknownProductBarcodeAnalysis
    ) {
        configureContentFragment(
            fragment = ProductAnalysisFragment.newInstance(barcodeAnalysis),
            barcodeAnalysis = barcodeAnalysis
        )
    }

    private fun configureDefaultBarcodeAnalysisView(
        barcodeAnalysis: DefaultBarcodeAnalysis
    ) = configureContentFragment(
        fragment = DefaultBarcodeAnalysisFragment.newInstance(barcodeAnalysis),
        barcodeAnalysis = barcodeAnalysis
    )

    private fun configureContentFragment(fragment: Fragment, barcodeAnalysis: BarcodeAnalysis) {
        replaceFragment(
            containerViewId = viewBinding.activityBarcodeInformationContent.id,
            fragment = fragment
        )

        // Change le texte de la toolbar
        changeToolbarText(barcodeAnalysis.barcode)
    }

    // ---- Utils ----

    private fun isInternetPermissionGranted(): Boolean {
        val permission: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun determineAPIRemote(barcodeType: BarcodeType): RemoteAPI =
        if(barcodeType == BarcodeType.UNKNOWN_PRODUCT) {
            when(settingsManager.apiChoose) {
                getString(R.string.preferences_entry_value_food) -> RemoteAPI.OPEN_FOOD_FACTS
                getString(R.string.preferences_entry_value_cosmetic) -> RemoteAPI.OPEN_BEAUTY_FACTS
                getString(R.string.preferences_entry_value_pet_food) -> RemoteAPI.OPEN_PET_FOOD_FACTS
                getString(R.string.preferences_entry_value_musicbrainz) -> RemoteAPI.MUSICBRAINZ
                else -> RemoteAPI.NONE
            }
        } else {
            when(barcodeType) {
                BarcodeType.FOOD -> RemoteAPI.OPEN_FOOD_FACTS
                BarcodeType.BEAUTY -> RemoteAPI.OPEN_BEAUTY_FACTS
                BarcodeType.PET_FOOD -> RemoteAPI.OPEN_PET_FOOD_FACTS
                BarcodeType.MUSIC -> RemoteAPI.MUSICBRAINZ
                BarcodeType.BOOK -> RemoteAPI.OPEN_LIBRARY
                else -> RemoteAPI.NONE
            }
        }

    // ---- UI ----

    fun showSnackbar(text: String) {
        Snackbar.make(viewBinding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun changeToolbarText(barcode: Barcode) {
        val tabText = getString(barcode.getBarcodeType().stringResource)
        supportActionBar?.title = tabText
    }

    // ---- Database Update ----

    private fun updateTypeIntoDatabase(barcodeAnalysis: BarcodeAnalysis) {
        val productName: String? = when (barcodeAnalysis) {
            is FoodBarcodeAnalysis -> barcodeAnalysis.name
            is BookBarcodeAnalysis -> barcodeAnalysis.title
            is MusicBarcodeAnalysis -> barcodeAnalysis.album?.let { album ->
                barcodeAnalysis.artists?.convertToString()?.let { artist ->
                    "$album - $artist"
                } ?: album
            }

            else -> null
        }

        val barcode = barcodeAnalysis.barcode
        val newBarcodeType = barcodeAnalysis.source.barcodeType
        if (!productName.isNullOrBlank()) {
            if (barcode.name != productName || barcode.getBarcodeType() != newBarcodeType)
                databaseBarcodeViewModel.updateTypeAndName(
                    barcode.scanDate,
                    newBarcodeType,
                    productName.trim()
                )
        } else {
            if (barcode.getBarcodeType() != newBarcodeType)
                databaseBarcodeViewModel.updateType(barcode.scanDate, newBarcodeType)
        }

        barcode.name = productName ?: ""
        barcode.type = newBarcodeType.name
    }

    /**
     * Met à jour le contenu du code-barres.
     */
    fun updateBarcodeContents(barcode: Barcode, newContents: String) {
        if(barcode.contents == newContents)
            return

        barcode.contents = newContents
        barcode.type = get<BarcodeType> { parametersOf(barcode) }.name
        barcode.name = ""
        barcode.updateCountry()

        databaseBarcodeViewModel.update(
            date = barcode.scanDate,
            contents = barcode.contents,
            barcodeType = barcode.getBarcodeType(),
            name = barcode.name
        )

        changeToolbarText(barcode)
        when {
            barcode.is1DProductBarcodeFormat -> fetchProductFromRemoteAPI(barcode)
            else -> configureDefaultBarcodeAnalysisView(DefaultBarcodeAnalysis(barcode))
        }
    }
}