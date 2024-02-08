package com.github.nearapps.barcodescanner.data.network

import android.accounts.NetworkErrorException
import com.github.nearapps.barcodescanner.common.exceptions.NoInternetConnectionException
import com.github.nearapps.barcodescanner.common.exceptions.WSException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.ExecutionException

class WSApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val response: Response

        try {
            response = chain.proceed(requestBuilder.build())
        } catch (e: Exception) {
            throw when(e){
                is ExecutionException -> NoInternetConnectionException()
                is IOException -> NoInternetConnectionException()
                else -> NetworkErrorException()
            }
        }

        if(!response.isSuccessful){
            throw WSException(response)
        }

        return response
    }
}