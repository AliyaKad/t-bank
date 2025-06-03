package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.mapper.GoalMapper
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(userId: Int): List<DomainGoal> {
        val dataGoals = goalRepository.getGoals(userId)
        return GoalMapper.mapToDomainList(dataGoals)
    }
}