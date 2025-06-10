package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.domain.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(params: CreateGoalParams): Result<Unit> {
        return repository.createGoal(params)
    }
}
