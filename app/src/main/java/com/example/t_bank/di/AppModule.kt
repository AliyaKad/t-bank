package com.example.t_bank.di

import com.example.t_bank.data.local.AppDatabase
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.remote.datasource.GoalDataSourceImpl
import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.data.remote.datasource.BudgetRemoteDataSource
import com.example.t_bank.data.repository.AuthRepository
import com.example.t_bank.data.repository.AuthRepositoryImpl
import com.example.t_bank.data.repository.BudgetRepository
import com.example.t_bank.data.repository.BudgetRepositoryImpl
import com.example.t_bank.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDistributionDao(appDatabase: AppDatabase): CategoryDistributionDao {
        return appDatabase.categoryDistributionDao()
    }

    @Provides
    @Singleton
    fun provideMonthlyBudgetDao(appDatabase: AppDatabase): MonthlyBudgetDao {
        return appDatabase.monthlyBudgetDao()
    }


    @Provides
    @Singleton
    fun provideGoalRepository(goalDataSource: GoalDataSourceImpl): GoalRepository {
        return GoalRepository(goalDataSource)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        categoryDao: CategoryDao,
        monthlyBudgetDao: MonthlyBudgetDao,
        categoryDistributionDao: CategoryDistributionDao,
        budgetRemoteDataSource: BudgetRemoteDataSource,
    ): SettingsRepository {
        return SettingsRepository(monthlyBudgetDao, categoryDao, categoryDistributionDao, budgetRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(repository: BudgetRepositoryImpl): BudgetRepository = repository



    @Provides
    @Singleton
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository = repository
}