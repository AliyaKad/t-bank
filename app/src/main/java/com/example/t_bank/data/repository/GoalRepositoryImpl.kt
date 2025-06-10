package com.example.t_bank.data.repository

import com.example.t_bank.Result
import com.example.t_bank.mapper.GoalMapper
import com.example.t_bank.data.remote.datasource.GoalDataSourceImpl
import com.example.t_bank.domain.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import com.example.t_bank.domain.usecase.model.DomainGoal
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val dataSource: GoalDataSourceImpl
) : GoalRepository {

    override suspend fun createGoal(params: CreateGoalParams): Result<Unit> {
        val request = GoalMapper.mapToData(params)
        return try {
            dataSource.createGoal(request)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteGoal(userId: Int, goalId: Int): Result<Unit> {
        return try {
            dataSource.deleteGoal(userId, goalId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getGoals(userId: Int): List<DomainGoal> {
        val dataGoals = dataSource.getGoals(userId) ?: emptyList()
        return GoalMapper.mapToDomainList(dataGoals)
    }

    override suspend fun updateGoal(userId: Int, goalId: Int, goal: DomainGoal): Result<Unit> {
        val request = GoalMapper.mapToData(goal)
        return try {
            dataSource.updateGoal(userId, goalId, request)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun addAmountToGoal(userId: Int, goalId: Int, amount: Double) {
        dataSource.addAmountToGoal(userId, goalId,amount)
    }
}
