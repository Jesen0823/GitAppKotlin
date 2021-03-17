package com.jesen.cod.gitappkotlin.network.interceptors

import android.util.Base64
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 登录鉴权拦截器
 * **/
@Deprecated("API旧协议,2020-11-13谷歌官方废弃")
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
            .apply {
                when {
                    original.url().pathSegments().contains("authorizations") -> {
                        val userCredential = "${AccountManager.username}:${AccountManager.passwd}"
                        val auth = "Basic" + String(
                            Base64.encode(userCredential.toByteArray(), Base64.DEFAULT)
                        ).trim()
                        header("Authorization", auth)
                    }
                    AccountManager.isLoggedIn() -> {
                        val auth = "Token" + AccountManager.token
                        header("Authorization", auth)
                    }
                    else -> removeHeader("Authorization")
                }
            }
            .build())
    }
}