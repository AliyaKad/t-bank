package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Goal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.remote.api.SmartBudgetApiService

class GoalDataSource(private val apiService: SmartBudgetApiService) {

    suspend fun createGoal(goalRequest: GoalRequest) {
        apiService.createGoal(goalRequest)
    }

    suspend fun getGoals(userId: Int): List<Goal>? {
        val response = apiService.getGoals(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest) {
        apiService.updateGoal(userId, goalId, goalRequest)
    }

    suspend fun deleteGoal(userId: Int, goalId: Int) {
        apiService.deleteGoal(userId, goalId)
    }
}
