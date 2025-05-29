package com.example.t_bank.di

import com.example.t_bank.data.repository.CategoryForTransactionsRepository
import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.data.repository.TransactionRepository
import com.example.t_bank.domain.usecase.GetCategoriesForTransactionsUseCase
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.GetTransactionsUseCase
import com.example.t_bank.mapper.CategoryDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCategoriesUseCase(
        repository: SettingsRepository,
        categoryMapper: CategoryDomainMapper
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository, categoryMapper)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetCategoriesForTransactionsUseCase(
        repository: CategoryForTransactionsRepository
    ): GetCategoriesForTransactionsUseCase {
        return GetCategoriesForTransactionsUseCase(repository)
    }
}