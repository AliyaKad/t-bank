package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Goal
import com.example.t_bank.data.model.GoalRequest

interface GoalDataSource {
    suspend fun createGoal(goalRequest: GoalRequest)
    suspend fun getGoals(userId: Int): List<Goal>?
    suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest)
    suspend fun deleteGoal(userId: Int, goalId: Int)
}
