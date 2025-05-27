package com.example.t_bank.di
import android.content.Context
import com.example.t_bank.data.remote.datasource.*
import com.example.t_bank.data.remote.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideBudgetRemoteDataSource(apiService: BudgetApiService): BudgetRemoteDataSource {
        return BudgetRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryDataSource(apiService: CategoryApiService, @ApplicationContext context: Context): CategoryDataSource {
        return CategoryDataSourceImpl(apiService, context)
    }

    @Provides
    @Singleton
    fun provideGoalDataSource(apiService: GoalApiService): GoalDataSource {
        return GoalDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNotificationDataSource(apiService: NotificationApiService): NotificationDataSource {
        return NotificationDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRuleDataSource(apiService: RuleApiService): RuleDataSource {
        return RuleDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideTransactionDataSource(apiService: TransactionApiService, @ApplicationContext context: Context): TransactionDataSource {
        return TransactionDataSourceImpl(apiService, context)
    }
}
