package com.lynnik.aroundme.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitCreator {

    fun initApi(): Api {
        val retrofit = initRetrofit()
        return retrofit.create(Api::class.java)
    }

    private fun initRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            readTimeout(RemoteSettings.READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(RemoteSettings.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(RemoteSettings.WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
                .baseUrl(RemoteSettings.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
    }
}