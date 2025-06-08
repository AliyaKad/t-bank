package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.remote.api.GoalApiService

class GoalDataSourceImpl(private val apiService: GoalApiService): GoalDataSource {

    override suspend fun createGoal(goalRequest: GoalRequest) {
        apiService.createGoal(goalRequest)
    }

    override suspend fun getGoals(userId: Int): List<DataGoal>? {
        val response = apiService.getGoals(userId)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest) {
        apiService.updateGoal(userId, goalId, goalRequest)
    }

    override suspend fun deleteGoal(userId: Int, goalId: Int) {
        apiService.deleteGoal(userId, goalId)
    }
}
