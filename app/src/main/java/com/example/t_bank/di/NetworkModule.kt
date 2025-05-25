package com.example.t_bank.di

import com.example.t_bank.data.remote.api.GoalApiService
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
    fun provideGoalApiService(retrofit: Retrofit): GoalApiService {
        return retrofit.create(GoalApiService::class.java)
    }
}
