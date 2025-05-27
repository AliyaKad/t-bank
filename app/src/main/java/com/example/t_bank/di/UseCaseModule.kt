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
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCategoriesUseCase(repository: SettingsRepository, categoryMapper: CategoryDomainMapper): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository, categoryMapper)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsUseCase(repository: TransactionRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: CategoryForTransactionsRepository): GetCategoriesForTransactionsUseCase {
        return GetCategoriesForTransactionsUseCase(repository)
    }
}