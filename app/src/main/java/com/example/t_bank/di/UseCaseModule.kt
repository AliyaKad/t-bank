package com.example.t_bank.di

import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.domain.usecase.CreateGoalUseCase
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.GetGoalsUseCase
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

    @Provides
    fun provideCreateGoalUseCase(goalRepository: GoalRepository): CreateGoalUseCase {
        return CreateGoalUseCase(goalRepository)
    }

    @Provides
    fun provideGetGoalsUseCase(goalRepository: GoalRepository): GetGoalsUseCase {
        return GetGoalsUseCase(goalRepository)
    }
}
