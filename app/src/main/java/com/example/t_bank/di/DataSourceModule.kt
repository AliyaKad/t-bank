package com.example.t_bank.di

import android.content.Context
import com.example.t_bank.data.remote.datasource.*
import com.example.t_bank.data.remote.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {


    @Provides
    @Singleton
    fun provideGoalDataSource(context: Context, apiService: GoalApiService): GoalDataSource {
        return GoalDataSourceImpl(apiService, context)
    }
}
