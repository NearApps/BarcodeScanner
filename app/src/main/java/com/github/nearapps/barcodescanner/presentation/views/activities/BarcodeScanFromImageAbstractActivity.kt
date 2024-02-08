package com.github.nearapps.barcodescanner.presentation.views.activities

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.extensions.getColorStateListFromAttrRes
import com.github.nearapps.barcodescanner.databinding.ActivityBarcodeScanFromImageBinding
import com.github.nearapps.barcodescanner.domain.library.BarcodeBitmapAnalyser
import com.google.zxing.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File

abstract class BarcodeScanFromImageAbstractActivity: BaseActivity() {

    private val barcodeBitmapAnalyser: BarcodeBitmapAnalyser by inject()

    private var zxingResult: Result? = null
    private var menuVisibility = false

    private val viewBinding: ActivityBarcodeScanFromImageBinding by lazy {
        ActivityBarcodeScanFromImageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityBarcodeScanFromImageToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.activityBarcodeScanFromImageCropImageView.clearImage()
        configureProgressBarColor()

        setContentView(viewBinding.root)
    }

    // ---- Menu ----

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_confirm, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_activity_confirm_item -> onSuccessfulImageScan(zxingResult)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(menu != null) {
            for (i in 0 until menu.size()) {
                menu.getItem(i).isVisible = menuVisibility
                menu.getItem(i).isEnabled = menuVisibility
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setMenuVisibility(visible: Boolean){
        if(menuVisibility!=visible) {
            menuVisibility=visible
            invalidateOptionsMenu()
        }
    }

    private fun configureProgressBarColor() {
        val mProgressBar = viewBinding.activityBarcodeScanFromImageCropImageView.findViewById<ProgressBar>(R.id.CropProgressBar)
        mProgressBar.indeterminateTintList = getColorStateListFromAttrRes(R.attr.colorPrimary)
    }

    // ---- Configure Crop ----

    /**
     * Configure tous les éléments utiles à la détection de code-barres dans l'image.
     * Le composant CropImageView permet de rogner l'image. Il permet donc d'analyser une partie
     * précise de l'image.
     */
    protected fun configureCropManagement(uri: Uri){

        // Insère l'image dans l'ImageCropView
        viewBinding.activityBarcodeScanFromImageCropImageView.setImageUriAsync(uri)

        var job: Job? = null

        // S'active à chaque appel de la méthode "imageCropView.getCroppedImageAsync()"
        viewBinding.activityBarcodeScanFromImageCropImageView.setOnCropImageCompleteListener { _, result ->
            val bitmap = result.getBitmap(this)

            if(bitmap != null){
                job?.cancel()
                job = lifecycleScope.launch(Dispatchers.IO) {
                    zxingResult = barcodeBitmapAnalyser.detectBarcodeFromBitmap(bitmap)
                    setMenuVisibility(zxingResult != null)
                }
            }
        }

        // S'active lorsque l'image a fini de se charger
        viewBinding.activityBarcodeScanFromImageCropImageView.setOnSetImageUriCompleteListener { _, _, _ ->
            viewBinding.activityBarcodeScanFromImageCropImageView.croppedImageAsync()
        }

        // S'active lors du déplacement de l'overlay
        viewBinding.activityBarcodeScanFromImageCropImageView.setOnSetCropOverlayMovedListener {
            viewBinding.activityBarcodeScanFromImageCropImageView.croppedImageAsync()
        }
    }

    protected abstract fun onSuccessfulImageScan(result: Result?)

    override fun finish() {
        removeTemporariesFiles()
        super.finish()
    }

    /**
     * La librairie android-image-cropper enregistre les crops d'image dans le répertoire de l'application.
     * Cette method permet donc de supprimer ces fichiers devenus inutiles.
     */
    private fun removeTemporariesFiles(){
        val dir = File(getExternalFilesDir(null), "Pictures")
        deleteRecursive(dir)
        dir.delete()
    }

    private fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) {
            fileOrDirectory.listFiles()?.forEach {
                deleteRecursive(it)
            }
        }
        fileOrDirectory.delete()
    }
}