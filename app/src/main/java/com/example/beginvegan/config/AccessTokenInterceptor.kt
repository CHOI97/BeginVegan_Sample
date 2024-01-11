package com.example.beginvegan.config

import com.example.beginvegan.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
//        val jwtToken: String? = sSharedPreferences.getString(ACCESS_TOKEN, null)
//        if (jwtToken != null) {
//            builder.addHeader(ACCESS_TOKEN, jwtToken)
//        }
        return chain.proceed(builder.build())
    }
}