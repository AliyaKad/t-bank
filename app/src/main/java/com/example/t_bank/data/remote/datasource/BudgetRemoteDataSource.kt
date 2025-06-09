package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus

interface BudgetRemoteDataSource {
    suspend fun setBudget(budgetRequest: BudgetRequest)
    suspend fun getBudgetStatus(userId: Long): BudgetStatus?
    suspend fun updateIncome(userId: Int, income: Double)
}
