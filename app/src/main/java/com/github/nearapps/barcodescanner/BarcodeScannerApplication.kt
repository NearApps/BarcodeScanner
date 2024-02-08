package com.github.nearapps.barcodescanner

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.github.nearapps.barcodescanner.common.injections.appModules
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BarcodeScannerApplication: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BarcodeScannerApplication)
            modules(appModules)
        }

        FirebaseCrashlytics
            .getInstance()
            .setCrashlyticsCollectionEnabled(
                !BuildConfig.DEBUG
            )
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
}