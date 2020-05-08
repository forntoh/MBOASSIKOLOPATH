package com.mboasikolopath.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // try the request
        var response = chain.proceed(request)

        var tryCount = 0

        while (!response.isSuccessful && tryCount < 5) {
            Log.d("API", "Request ${response.request().url()} is not successful - $tryCount")

            tryCount++

            // retry the request
            response.close()
            response = chain.proceed(request)
        }
        return response
    }

}