package com.example.t_bank.di

import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.mapper.CategoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCategoriesUseCase(repository: SettingsRepository, categoryMapper: CategoryMapper): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository, categoryMapper)
    }
}