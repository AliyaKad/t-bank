package com.example.t_bank.data.repository

import com.example.t_bank.data.local.dao.BudgetCategoryResult
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.domain.usecase.model.BudgetWithCategories
import com.example.t_bank.presentation.model.Category
import javax.inject.Inject

class MonthlyBudgetRepository @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao
) {
    suspend fun getBudgetAndCategoriesByMonth(month: String): BudgetWithCategories? {
        val results = monthlyBudgetDao.getBudgetAndCategoriesByMonth(month)
        return mapToBudgetWithCategories(results)
    }

    private fun mapToBudgetWithCategories(results: List<BudgetCategoryResult>): BudgetWithCategories? {
        if (results.isEmpty()) return null

        val totalBudget = results.first().totalBudget
        val categories = results.map { result ->
            Category(
                name = result.categoryName,
                iconResId = result.categoryIconResId,
                colorResId = result.categoryColorResId,
                percentage = result.categoryPercentage
            )
        }

        return BudgetWithCategories(totalBudget, categories)
    }
}