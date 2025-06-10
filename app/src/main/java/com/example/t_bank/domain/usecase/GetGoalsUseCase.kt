package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.DomainGoal
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(userId: Int): List<DomainGoal> {
        return repository.getGoals(userId)
    }
}
