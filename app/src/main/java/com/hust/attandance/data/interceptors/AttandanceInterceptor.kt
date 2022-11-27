package com.hust.attandance.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AttandanceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (request.method == "POST")
            if (request.headers["Accept"].isNullOrEmpty()) {
                builder.header("Accept", "application/json")
            }

        return chain.proceed(builder.build())
    }
}