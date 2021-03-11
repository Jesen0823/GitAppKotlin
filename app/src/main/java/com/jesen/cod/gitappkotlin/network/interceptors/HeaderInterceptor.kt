package com.jesen.cod.gitappkotlin.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
            .apply {
                header("Accept-Encoding", "gzip")
                header("Accept", "application/json")
                //header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                header("Content-Type", "application/json")
                method(original.method(), original.body())
            }.build()
        )
    }
}