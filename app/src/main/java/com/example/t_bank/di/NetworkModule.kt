package com.example.t_bank.di

import com.example.t_bank.data.remote.api.BudgetApiService
import com.example.t_bank.data.remote.api.CategoryApiService
import com.example.t_bank.data.remote.api.GoalApiService
import com.example.t_bank.data.remote.api.NotificationApiService
import com.example.t_bank.data.remote.api.RuleApiService
import com.example.t_bank.data.remote.api.TransactionApiService
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
    fun provideBudgetApiService(retrofit: Retrofit): BudgetApiService {
        return retrofit.create(BudgetApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
        return retrofit.create(CategoryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoalApiService(retrofit: Retrofit): GoalApiService {
        return retrofit.create(GoalApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService {
        return retrofit.create(NotificationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRuleApiService(retrofit: Retrofit): RuleApiService {
        return retrofit.create(RuleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService {
        return retrofit.create(TransactionApiService::class.java)
    }
}
