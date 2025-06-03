package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.mapper.GoalMapper
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(params: CreateGoalParams): Result<Unit> {
        val goalRequest: GoalRequest = GoalMapper.mapToData(params)
        return goalRepository.createGoal(goalRequest)
    }
}
