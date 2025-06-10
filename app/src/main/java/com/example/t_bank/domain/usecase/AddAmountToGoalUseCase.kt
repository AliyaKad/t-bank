package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.GoalRepository
import javax.inject.Inject

class AddAmountToGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(userId: Int, goalId: Int, amount: Double) {
        repository.addAmountToGoal(userId, goalId, amount)
    }
}
