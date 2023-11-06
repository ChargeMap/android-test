package com.example.androidtest

import okhttp3.Interceptor
import okhttp3.Response

class UrlLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        println("Outgoing URL: $url")
        return chain.proceed(request)
    }
}