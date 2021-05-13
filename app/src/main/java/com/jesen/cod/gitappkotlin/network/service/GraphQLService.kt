package com.jesen.cod.gitappkotlin.network.service

import com.apollographql.apollo.ApolloClient
import com.jesen.cod.gitappkotlin.network.interceptors.AuthInterceptor2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private const val GRAPH_BASE_URL = "https://api.github.com/graphql"

val apolloClient by lazy {
    ApolloClient.builder()
        .serverUrl(GRAPH_BASE_URL)
        .okHttpClient(
            OkHttpClient.Builder().addInterceptor(AuthInterceptor2())
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC)
                ).build()
        )
        .build()
}