package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus
import com.example.t_bank.data.remote.api.SmartBudgetApiService

class BudgetRemoteDataSource(private val apiService: SmartBudgetApiService) {

    suspend fun setBudget(budgetRequest: BudgetRequest) {
        apiService.setBudget(budgetRequest)
    }

    suspend fun getBudgetStatus(userId: Int): BudgetStatus? {
        val response = apiService.getBudgetStatus(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateIncome(userId: Int, income: Double) {
        val incomeUpdate = mapOf("income" to income)
        apiService.updateIncome(userId, incomeUpdate)
    }
}
