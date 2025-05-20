package com.example.t_bank.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://virtserver.swaggerhub.com/LizaMusina/Smart_Budget/1.0.0/api/v1/ "

    val api: SmartBudgetApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmartBudgetApiService::class.java)
    }
}
