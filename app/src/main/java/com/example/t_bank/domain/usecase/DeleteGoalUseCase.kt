package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.data.repository.GoalRepository
import javax.inject.Inject

class DeleteGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository) {

    suspend operator fun invoke(userId: Int, goalId: Int): Result<Unit> {
        return goalRepository.deleteGoal(userId, goalId)
    }
}
