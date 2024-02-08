package com.github.nearapps.barcodescanner.presentation.views.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.github.nearapps.barcodescanner.common.extensions.parcelable
import com.github.nearapps.barcodescanner.common.extensions.toIntent
import com.github.nearapps.barcodescanner.presentation.intent.createPickImageIntent
import com.google.zxing.Result

open class BarcodeScanFromImageGalleryActivity: BarcodeScanFromImageAbstractActivity() {

    companion object {
        private const val URI_INTENT_KEY = "uriIntentKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = getImageUri()
        if(uri == null) pickImageFromGallery() else configureCropManagement(uri)
    }

    /**
     * Gère le retour de la galerie d'image.
     */
    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val uri: Uri? = result?.data?.data
            if (result.resultCode == Activity.RESULT_OK && uri != null) {
                if (!intent.hasExtra(URI_INTENT_KEY))
                    intent.putExtra(URI_INTENT_KEY, uri)
                configureCropManagement(uri)
            } else {
                finish()
            }
        }

    /**
     * Prépare et ouvre la gallery pour récupérer une image.
     */
    private fun pickImageFromGallery(){
        val imagePickerIntent = createPickImageIntent()
        resultLauncher.launch(imagePickerIntent)
    }

    /**
     * Permet de récupérer l'Uri via l'intent de l'Activity si elle a été stockée, évitant ainsi de
     * repasser par la gallery pour récupérer l'image (utile lors de la rotation de l'écran).
     */
    private fun getImageUri(): Uri? = if(intent.hasExtra(URI_INTENT_KEY)) {
        intent.parcelable(URI_INTENT_KEY, Uri::class.java)
    } else null


    override fun onSuccessfulImageScan(result: Result?) {
        setResult(Activity.RESULT_OK, result?.toIntent())
        finish()
    }
}