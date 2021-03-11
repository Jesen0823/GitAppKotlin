package com.jesen.cod.gitappkotlin.network

import com.jesen.cod.common.ext.ensureDir
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.network.interceptors.AcceptInterceptor
import com.jesen.cod.gitappkotlin.network.interceptors.AuthInterceptor
import com.jesen.cod.gitappkotlin.network.interceptors.HeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com"
private const val BASE_URL_NEW = "https://github.com/"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply {
        ensureDir()
    }
}

val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addCallAdapterFactory(
            RxJavaCallAdapterFactory2.createWithSchedulers(
                Schedulers.io(),
                AndroidSchedulers.mainThread()
            )
        )
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .addInterceptor(AcceptInterceptor())
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
                //.enableTls12OnPreLollipop()
                .build()
        )
        .baseUrl(BASE_URL_NEW)
        .build()

}