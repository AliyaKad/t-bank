package com.example.t_bank.di

import com.example.t_bank.data.local.AppDatabase
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.repository.AllBudgetsRepositoryImpl
import com.example.t_bank.domain.repository.AuthRepository
import com.example.t_bank.data.repository.AuthRepositoryImpl
import com.example.t_bank.domain.repository.BudgetRepository
import com.example.t_bank.data.repository.BudgetRepositoryImpl
import com.example.t_bank.data.repository.CategoryRepositoryImpl
import com.example.t_bank.data.repository.GoalRepositoryImpl
import com.example.t_bank.data.repository.MonthlyBudgetRepositoryImpl
import com.example.t_bank.data.repository.SettingsRepositoryImpl
import com.example.t_bank.data.repository.TransactionRepositoryImpl
import com.example.t_bank.domain.repository.AllBudgetsRepository
import com.example.t_bank.domain.repository.CategoryRepository
import com.example.t_bank.domain.repository.GoalRepository
import com.example.t_bank.domain.repository.MonthlyBudgetRepository
import com.example.t_bank.domain.repository.SettingsRepository
import com.example.t_bank.domain.repository.TransactionRepository
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
    fun provideBudgetRepository(repository: BudgetRepositoryImpl): BudgetRepository = repository



    @Provides
    @Singleton
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository = repository

    @Provides
    @Singleton
    fun provideTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository = repository

    @Provides
    @Singleton
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository = repository

    @Provides
    @Singleton
    fun provideAllBudgetsRepository(repository: AllBudgetsRepositoryImpl): AllBudgetsRepository = repository

    @Provides
    @Singleton
    fun provideCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository = repository

    @Provides
    @Singleton
    fun provideGoalRepository(repository: GoalRepositoryImpl): GoalRepository = repository

    @Provides
    @Singleton
    fun provideMonthlyRepository(repository: MonthlyBudgetRepositoryImpl): MonthlyBudgetRepository = repository
}