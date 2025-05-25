package com.example.t_bank.data.repository

import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.Result
import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.remote.datasource.GoalDataSourceImpl


class GoalRepository(private val goalDataSource: GoalDataSourceImpl) {

    suspend fun createGoal(goalRequest: GoalRequest): Result<Unit> {
        return try {
            goalDataSource.createGoal(goalRequest)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    suspend fun deleteGoal(userId: Int, goalId: Int): Result<Unit> {
        return try {
            goalDataSource.deleteGoal(userId, goalId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    suspend fun getGoals(userId: Int): List<DataGoal> {
        return goalDataSource.getGoals(userId) ?: emptyList()
    }
}
