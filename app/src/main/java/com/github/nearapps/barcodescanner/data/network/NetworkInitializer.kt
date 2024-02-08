package com.github.nearapps.barcodescanner.data.network

import android.content.Context
import com.github.nearapps.barcodescanner.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val WS_CALL_TIMEOUT_SECONDS = 10L
private const val CACHE_MAX_SIZE = 10L * 1024L * 1024L
private const val USER_AGENT = "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME} (https://github.com/NearApps/BarcodeScanner)"

fun createApiClient(context: Context, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun createHttpClient(context: Context): OkHttpClient =
    OkHttpClient.Builder()
        .addNetworkInterceptor(UserAgentInterceptor(USER_AGENT))
        .cache(createHttpCache(context))
        .addNetworkInterceptor(CacheInterceptor())
        .addInterceptor(WSApiInterceptor())
        .callTimeout(WS_CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

private fun createHttpCache(context: Context): Cache =
    Cache(File(context.cacheDir, "http-cache"), CACHE_MAX_SIZE)