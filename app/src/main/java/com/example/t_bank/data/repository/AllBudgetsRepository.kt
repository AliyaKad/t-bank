package com.example.t_bank.data.repository

import android.util.Log
import com.example.t_bank.data.local.dao.BudgetCategoryResultForMonth
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import com.example.t_bank.presentation.model.CategoryForMonths
import javax.inject.Inject

class AllBudgetsRepository @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao
) {
    suspend fun getAllBudgets(): List<BudgetForAllMonths> {
        val allResults = monthlyBudgetDao.getAllBudgets()
        Log.d("AllBudgetsRepository", "Fetched ${allResults.size} raw results from DAO.")
        allResults.forEach { result ->
            Log.d(
                "AllBudgetsRepository",
                "Raw result: month=${result.month}, category=${result.categoryName}, budget=${result.budget}, spent=${result.amountSpent}"
            )
        }

        val groupedResults = groupResultsByMonth(allResults)
        Log.d("AllBudgetsRepository", "Grouped ${groupedResults.size} budgets by month.")
        groupedResults.forEach { budget ->
            Log.d(
                "AllBudgetsRepository",
                "Grouped budget: month=${budget.month}, totalBudget=${budget.totalBudget}, categories=${budget.categories.size}"
            )
            budget.categories.forEach { category ->
                Log.d(
                    "AllBudgetsRepository",
                    "Category: name=${category.name}, budget=${category.budget}, spent=${category.amountSpent}"
                )
            }
        }
        return groupedResults
    }

    private fun groupResultsByMonth(results: List<BudgetCategoryResultForMonth>): List<BudgetForAllMonths> {
        val groupedResults = results.groupBy { it.month }
        return groupedResults.map { (month, monthResults) ->
            BudgetForAllMonths(
                totalBudget = monthResults.first().totalBudget,
                month = month,
                categories = monthResults.map { result ->
                    CategoryForMonths(
                        name = result.categoryName,
                        colorResId = result.categoryColorResId,
                        budget = result.budget,
                        amountSpent = result.amountSpent
                    )
                }
            )
        }
    }
}
