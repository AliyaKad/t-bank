package com.example.t_bank.data.repository

import android.util.Log
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.local.entity.CategoryDistributionEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import javax.inject.Inject
import androidx.room.Transaction
import com.example.t_bank.data.local.entity.CategoryExpenseEntity
import com.example.t_bank.data.remote.datasource.BudgetRemoteDataSource
import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.domain.repository.SettingsRepository
import com.example.t_bank.domain.usecase.model.Category
import com.example.t_bank.toApiModel
import com.example.t_bank.toEntity


class SettingsRepositoryImpl @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao,
    private val categoryDao: CategoryDao,
    private val categoryDistributionDao: CategoryDistributionDao,
    private val budgetRemoteDataSource: BudgetRemoteDataSource
) : SettingsRepository {

    @Transaction
    override suspend fun saveMonthlyBudget(
        userId: Long,
        month: String,
        totalBudget: Float,
        categories: List<Category>
    ) {
        try {
            val existingBudget = monthlyBudgetDao.getBudgetByMonth(month)

            var budgetId: Long

            if (existingBudget != null) {
                val updatedBudget = existingBudget.copy(totalBudget = totalBudget)
                monthlyBudgetDao.updateMonthlyBudget(updatedBudget)
                budgetId = existingBudget.id.toLong()
                categoryDistributionDao.deleteByBudgetId(budgetId.toInt())

            } else {
                val newBudget = MonthlyBudgetEntity(id = 0, month = month, totalBudget = totalBudget)
                budgetId = monthlyBudgetDao.insertMonthlyBudget(newBudget)
            }
            val allCategoriesInDb = categoryDao.getAllCategories()
            val categoryMapInDb = allCategoriesInDb.associateBy { it.name }
            val categoryEntities = categories.map { it.toEntity() }

            categoryEntities.forEach { newCategory ->
                val existingCategory = categoryMapInDb[newCategory.name]
                if (existingCategory == null) {
                    categoryDao.insertCategories(newCategory)
                } else {
                    if (newCategory != existingCategory) {
                        categoryDao.updateCategory(newCategory.copy(id = existingCategory.id))
                    }
                }
            }

            val allCategories = categoryDao.getAllCategories()
            val categoryMap = allCategories.associateBy { it.name }
            val distributions = categoryEntities.mapNotNull { category ->
                categoryMap[category.name]?.let { savedCategory ->
                    CategoryDistributionEntity(
                        id = 0,
                        budgetId = budgetId.toInt(),
                        categoryId = savedCategory.id
                    )
                }
            }

            if (distributions.isNotEmpty()) {
                categoryDistributionDao.insertCategoryDistributions(distributions)
            } else {
                return
            }

            val apiBudget = BudgetRequest(
                userId = userId,
                income = totalBudget.toInt(),
                categories = categories.map { it.toApiModel() }
            )
            budgetRemoteDataSource.setBudget(apiBudget)

        } catch (e: Exception) {
            throw RuntimeException("Failed to save or update budget", e)
        }
    }

}
