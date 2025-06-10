package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.BudgetForAllMonths

interface AllBudgetsRepository {
    suspend fun getAllBudgets(): List<BudgetForAllMonths>
}
