package com.github.nearapps.barcodescanner.presentation.views.activities

import android.os.Bundle
import com.github.nearapps.barcodescanner.common.extensions.setImageFromWeb
import com.github.nearapps.barcodescanner.common.utils.IMAGE_URI_KEY
import com.github.nearapps.barcodescanner.databinding.ActivityImageFullScreenBinding

class ImageFullScreenActivity : BaseActivity() {

    private val viewBinding: ActivityImageFullScreenBinding by lazy { ActivityImageFullScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(viewBinding.activityImageFullScreenToolbar)
        supportActionBar?.title = null

        val imageUri: String? = intent.getStringExtra(IMAGE_URI_KEY)

        viewBinding.activityImageFullScreenImageView.setImageFromWeb(imageUri)

        setContentView(viewBinding.root)
    }
}