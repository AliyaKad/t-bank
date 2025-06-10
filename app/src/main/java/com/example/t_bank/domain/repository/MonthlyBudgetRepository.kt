package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.BudgetWithCategories
import kotlinx.coroutines.flow.Flow

interface MonthlyBudgetRepository {
    suspend fun getBudgetAndCategoriesByMonth(month: String): BudgetWithCategories?
}
