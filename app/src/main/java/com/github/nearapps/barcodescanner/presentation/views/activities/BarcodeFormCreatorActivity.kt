package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.serializable
import com.github.nearapps.barcodescanner.common.utils.BARCODE_TYPE_ENUM_KEY
import com.github.nearapps.barcodescanner.databinding.ActivityBarcodeFormCreatorBinding
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeFormatDetails
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.AbstractBarcodeFormCreatorFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

/**
 * Activity contenant les formulaires de créations de code-barres. Il contient un Fragment
 * contenant le formulaire. Le Fragment est choisie en fonction du type de code-barre choisie via
 * l'Intent.
 */
class BarcodeFormCreatorActivity : BaseActivity() {

    private var formCreateBarcodeFragment: AbstractBarcodeFormCreatorFragment? = null

    private val allBarcodeFormat: BarcodeFormatDetails? by lazy {
        intent.serializable(BARCODE_TYPE_ENUM_KEY, BarcodeFormatDetails::class.java)
    }

    private val viewBinding: ActivityBarcodeFormCreatorBinding by lazy { ActivityBarcodeFormCreatorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityBarcodeFormCreatorToolbar.toolbar)

        lifecycleScope.launch(Dispatchers.Main) {
            allBarcodeFormat?.apply(::configureHeader)
            allBarcodeFormat?.apply(::configureFormFragment)
        }

        // onBackPressed
        onBackPressedDispatcher.addCallback(this) {

            formCreateBarcodeFragment?.let { fragment ->
                fragment.hideSoftKeyboard()
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    setCustomAnimations(R.anim.barcode_creator_enter, R.anim.barcode_creator_exit)
                    remove(fragment)
                }
            }

            finishAfterTransition()
        }

        setContentView(viewBinding.root)
    }

    // ---- Header ----

    /**
     * Configure le Fragment contenant le Header de l'Activity.
     */
    private fun configureHeader(barcodeFormatDetails: BarcodeFormatDetails) {
        val imageView = viewBinding.activityBarcodeFormCreatorHeader.templateItemBarcodeCreatorImageView
        val textView = viewBinding.activityBarcodeFormCreatorHeader.templateItemBarcodeCreatorTextView
        textView.text = getString(barcodeFormatDetails.stringResource)
        imageView.setImageResource(barcodeFormatDetails.drawableResource)
    }

    // ---- Formulaire ----

    /**
     * Configure le Fragment contenant le formulaire de création.
     */
    private fun configureFormFragment(barcodeFormatDetails: BarcodeFormatDetails){
        formCreateBarcodeFragment = get<AbstractBarcodeFormCreatorFragment> {
            parametersOf(barcodeFormatDetails)
        }
        formCreateBarcodeFragment?.let { fragment ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(R.anim.barcode_creator_enter, R.anim.barcode_creator_exit)
                replace(viewBinding.activityBarcodeFormCreatorFragment.id, fragment)
            }
        }
    }

    fun configureErrorMessage(message: String) {
        viewBinding.activityBarcodeFormCreatorErrorLayout.visibility = View.VISIBLE
        viewBinding.activityBarcodeFormCreatorErrorTextView.text = message
    }

    fun hideErrorMessage() {
        viewBinding.activityBarcodeFormCreatorErrorLayout.visibility = View.GONE
        viewBinding.activityBarcodeFormCreatorErrorTextView.text = ""
    }
}