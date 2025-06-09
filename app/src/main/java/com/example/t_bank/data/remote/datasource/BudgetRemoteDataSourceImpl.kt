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

        val body = response.body()
        if (body == null || !body.contains("successfully", true)) {
            throw Exception("Неверный формат ответа")
        }
    }

    override suspend fun getBudgetStatus(userId: Long): BudgetStatus? {
        return try {
            val response = apiService.getBudgetStatus(userId)

            Log.d("BudgetRemoteDataSource", "Response code: ${response.code()}")
            Log.d("BudgetRemoteDataSource", "Response headers: ${response.headers()}")
            Log.d("BudgetRemoteDataSource", "Is successful: ${response.isSuccessful}")

            val body = response.body()?.toString() ?: "null"
            Log.d("BudgetRemoteDataSource", "Response body: $body")

            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                Log.e("BudgetRemoteDataSource", "API вернул ошибку: ${response.code()}, body: $body")
                null
            }
        } catch (e: Exception) {
            Log.e("BudgetRemoteDataSource", "Ошибка сети или парсинга", e)
            null
        }
    }

    override suspend fun updateIncome(userId: Int, income: Double) {
        val incomeUpdate = mapOf("income" to income)
        apiService.updateIncome(userId, incomeUpdate)
    }
}
