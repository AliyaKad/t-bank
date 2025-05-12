package com.example.t_bank.di

import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.SaveDefaultCategoriesUseCase
import com.example.t_bank.domain.usecase.SaveMonthlyBudgetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSaveDefaultCategoriesUseCase(repository: SettingsRepository): SaveDefaultCategoriesUseCase {
        return SaveDefaultCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveMonthlyBudgetUseCase(repository: SettingsRepository): SaveMonthlyBudgetUseCase {
        return SaveMonthlyBudgetUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: SettingsRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }
}