package com.example.t_bank.domain.usecase


import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.repository.GoalRepository
import com.example.t_bank.domain.usecase.model.DomainGoal

class GetGoalsUseCase(private val goalRepository: GoalRepository) {
    suspend operator fun invoke(userId: Int): List<DomainGoal> {
        val dataGoals = goalRepository.getGoals(userId)
        return dataGoals.map { mapToDomainGoal(it) }
    }

    private fun mapToDomainGoal(dataGoal: DataGoal): DomainGoal {
        return DomainGoal(
            name = dataGoal.name,
            targetAmount = dataGoal.targetAmount,
            savedAmount = dataGoal.savedAmount,
            recommendedMonthlySaving = dataGoal.recommendedMonthlySaving,
            deadline = dataGoal.deadline,
            status = when (dataGoal.status) {
                "COMPLETED" -> DomainGoal.Status.COMPLETED
                else -> DomainGoal.Status.IN_PROGRESS
            }
        )
    }
}
