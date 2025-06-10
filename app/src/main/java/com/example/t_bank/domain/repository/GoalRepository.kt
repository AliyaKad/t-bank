package com.example.t_bank.domain.repository

import com.example.t_bank.Result
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun createGoal(params: CreateGoalParams): Result<Unit>
    suspend fun deleteGoal(userId: Int, goalId: Int): Result<Unit>
    suspend fun getGoals(userId: Int): List<DomainGoal>
    suspend fun updateGoal(userId: Int, goalId: Int, goal: DomainGoal): Result<Unit>
    suspend fun addAmountToGoal(userId: Int, goalId: Int, amount: Double)
}
