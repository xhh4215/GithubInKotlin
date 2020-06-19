package com.bennyhuo.github.network

import com.apollographql.apollo.ApolloClient
import com.bennyhuo.github.network.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

private const val BASE_URL = "https://api.github.com/graphql"

val apolloClient by lazy {
    ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(OkHttpClient.Builder().addInterceptor(AuthInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build())
            .build()
}