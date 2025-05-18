package com.example.t_bank.di

import android.content.Context
import com.example.t_bank.data.local.AppDatabase
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.FinancialGoalDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.repository.FinancialGoalRepository
import com.example.t_bank.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

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
    fun provideFinancialGoalDao(appDatabase: AppDatabase): FinancialGoalDao {
        return appDatabase.financialGoalDao()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        categoryDao: CategoryDao,
        monthlyBudgetDao: MonthlyBudgetDao,
        categoryDistributionDao: CategoryDistributionDao
    ): SettingsRepository {
        return SettingsRepository(categoryDao, monthlyBudgetDao, categoryDistributionDao)
    }

    @Provides
    @Singleton
    fun provideFinancialGoalRepository(financialGoalDao: FinancialGoalDao): FinancialGoalRepository {
        return FinancialGoalRepository(financialGoalDao)
    }
}