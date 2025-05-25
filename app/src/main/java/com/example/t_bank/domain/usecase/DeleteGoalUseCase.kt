package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.data.repository.GoalRepository

class DeleteGoalUseCase(private val goalRepository: GoalRepository) {

    suspend operator fun invoke(userId: Int, goalId: Int): Result<Unit> {
        return goalRepository.deleteGoal(userId, goalId)
    }
}
