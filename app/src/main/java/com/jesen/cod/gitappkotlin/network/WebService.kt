package com.jesen.cod.gitappkotlin.network

import com.google.gson.GsonBuilder
import com.jesen.cod.common.ext.ensureDir
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.network.compat.enableTls12OnPreLollipop
import com.jesen.cod.gitappkotlin.network.interceptors.*
import com.jesen.cod.gitappkotlin.network.service.SSLSocketFactoryCompat
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

//通过一个 QueryParameter 让 CacheInterceptor 添加 no-cache
const val FORCE_NETWORK = "forceNetwork"


/*该请求方式GitHub官方不用来鉴权*/
private const val BASE_URL = "https://api.github.com"

/*用来鉴权*/
private const val BASE_URL_NEW = "https://github.com/"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply {
        ensureDir()
    }
}

val gson = GsonBuilder().setLenient().create()

fun getSSLSocketFactory(): SSLSocketFactory? {
    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(null, null, SecureRandom())
    return sslContext.socketFactory
}

val retrofit by lazy {
    buildRetrofit(BASE_URL_NEW)
}

val retrofitOld by lazy {
    buildRetrofit(BASE_URL)
}


private fun buildRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addCallAdapterFactory(
            RxJavaCallAdapterFactory2.createWithSchedulers(
                Schedulers.io(),
                AndroidSchedulers.mainThread()
            )
        )
        .client(
            OkHttpClient.Builder()
                //.sslSocketFactory(SSLSocketFactoryCompat())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .addInterceptor(AcceptInterceptor())
                //.addInterceptor(AuthInterceptor2())
                .addInterceptor(CacheInterceptor())
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
                .enableTls12OnPreLollipop()
                .build()
        )
        .baseUrl(baseUrl)
        .build()
}