package com.example.t_bank.data.repository

import com.example.t_bank.data.local.dao.BudgetCategoryResultForMonth
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.domain.repository.AllBudgetsRepository
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import com.example.t_bank.presentation.model.CategoryForMonths
import javax.inject.Inject

class AllBudgetsRepositoryImpl @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao
) : AllBudgetsRepository {

    override suspend fun getAllBudgets(): List<BudgetForAllMonths> {
        val allResults = monthlyBudgetDao.getAllBudgets()

        val groupedResults = groupResultsByMonth(allResults)
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
