package com.example.t_bank.data.repository

import com.example.t_bank.R
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.remote.datasource.BudgetRemoteDataSource
import com.example.t_bank.domain.repository.BudgetRepository
import com.example.t_bank.domain.usecase.model.BudgetStatus
import com.example.t_bank.domain.usecase.model.BudgetCategoryStatus
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val remoteDataSource: BudgetRemoteDataSource,
    private val categoryDao: CategoryDao
) : BudgetRepository {

    override suspend fun getBudgetStatusWithColors(userId: Long): BudgetStatus {
        val response = remoteDataSource.getBudgetStatus(userId)
            ?: throw Exception("Ошибка загрузки бюджета")


        val latestCategories = categoryDao.getLatestCategories()
        val latestCategoryMap = latestCategories.associateBy { it.name }


        val mappedCategories = response.categories.map { apiCategory ->
            val dbCategory = latestCategoryMap[apiCategory.name]


            BudgetCategoryStatus(
                name = apiCategory.name,
                spent = apiCategory.spent,
                remaining = apiCategory.remaining,
                limit = apiCategory.limit,
                colorResId = dbCategory?.colorResId ?: R.color.blue
            ).apply {
            }
        }
        return BudgetStatus(
            income = response.income,
            categories = mappedCategories
        )
    }
}
