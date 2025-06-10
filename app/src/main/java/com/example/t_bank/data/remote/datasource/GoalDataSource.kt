package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest

interface GoalDataSource {
    suspend fun createGoal(goalRequest: GoalRequest)
    suspend fun getGoals(userId: Int): List<DataGoal>?
    suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest)
    suspend fun deleteGoal(userId: Int, goalId: Int)
    suspend fun addAmountToGoal(userId: Int, goalId: Int, amount: Double): DataGoal?
}

