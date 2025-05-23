package com.example.t_bank.data.repository

import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.remote.datasource.GoalDataSource
import com.example.t_bank.Result
import com.example.t_bank.data.model.DataGoal


class GoalRepository(private val goalDataSource: GoalDataSource) {

    suspend fun createGoal(goalRequest: GoalRequest): Result<Unit> {
        return try {
            goalDataSource.createGoal(goalRequest)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    suspend fun getGoals(userId: Int): List<DataGoal> {
        return goalDataSource.getGoals(userId) ?: emptyList()
    }
}
