package com.example.t_bank.di

import com.example.t_bank.data.remote.api.SmartBudgetApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://virtserver.swaggerhub.com/LizaMusina/Smart_Budget/1.0.0/ ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSmartBudgetApiService(retrofit: Retrofit): SmartBudgetApiService {
        return retrofit.create(SmartBudgetApiService::class.java)
    }
}
