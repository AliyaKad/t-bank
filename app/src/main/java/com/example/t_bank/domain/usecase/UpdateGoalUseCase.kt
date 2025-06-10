package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.domain.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.DomainGoal
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(userId: Int, goalId: Int, goal: DomainGoal): Result<Unit> {
        return repository.updateGoal(userId, goalId, goal)
    }
}
