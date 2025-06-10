package com.example.t_bank.data.remote.datasource

import android.content.Context
import android.util.Log
import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus
import com.example.t_bank.data.remote.api.BudgetApiService

class BudgetRemoteDataSourceImpl(private val apiService: BudgetApiService, private val context: Context) : BudgetRemoteDataSource {

    override suspend fun setBudget(budgetRequest: BudgetRequest) {
        val response = apiService.setBudget(budgetRequest)
        if (!response.isSuccessful) throw Exception("Ошибка сохранения бюджета")
    }

    override suspend fun getBudgetStatus(userId: Long): BudgetStatus? {
        return try {
            val response = apiService.getBudgetStatus(userId)

            val body = response.body()?.toString() ?: "null"

            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateIncome(userId: Int, income: Double) {
        val incomeUpdate = mapOf("income" to income)
        apiService.updateIncome(userId, incomeUpdate)
    }
}
