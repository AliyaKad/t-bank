package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.mapper.GoalMapper
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(userId: Int, goalId: Int, domainGoal: DomainGoal): Result<Unit> {
        val goalRequest = GoalMapper.mapToData(domainGoal)
        return goalRepository.updateGoal(userId, goalId, goalRequest)
    }
}
