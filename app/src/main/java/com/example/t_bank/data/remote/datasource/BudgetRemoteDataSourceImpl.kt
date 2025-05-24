package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus
import com.example.t_bank.data.remote.api.BudgetApiService

class BudgetRemoteDataSourceImpl(private val apiService: BudgetApiService) : BudgetRemoteDataSource {

    override suspend fun setBudget(budgetRequest: BudgetRequest) {
        apiService.setBudget(budgetRequest)
    }

    override suspend fun getBudgetStatus(userId: Int): BudgetStatus? {
        val response = apiService.getBudgetStatus(userId)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateIncome(userId: Int, income: Double) {
        val incomeUpdate = mapOf("income" to income)
        apiService.updateIncome(userId, incomeUpdate)
    }
}
