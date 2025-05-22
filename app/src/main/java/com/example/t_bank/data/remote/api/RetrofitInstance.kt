package com.example.t_bank.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val api: SmartBudgetApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://virtserver.swaggerhub.com/LizaMusina/Smart_Budget/1.0.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartBudgetApiService::class.java)
    }
}

