package com.example.t_bank.mapper

import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.presentation.model.Goal as PresentationGoal

object GoalMapper {

    fun mapToDomain(dataGoal: DataGoal): DomainGoal {
        return DomainGoal(
            id = dataGoal.id,
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

    fun mapToDomainList(dataGoals: List<DataGoal>): List<DomainGoal> {
        return dataGoals.map { mapToDomain(it) }
    }

    fun mapToData(domainGoal: DomainGoal): GoalRequest {
        return GoalRequest(
            userId = domainGoal.id,
            name = domainGoal.name,
            targetAmount = domainGoal.targetAmount,
            deadline = domainGoal.deadline
        )
    }

    fun mapToData(params: CreateGoalParams): GoalRequest {
        return GoalRequest(
            userId = params.userId,
            name = params.name,
            targetAmount = params.targetAmount,
            deadline = params.deadline
        )
    }

    fun mapToPresentation(domainGoal: DomainGoal): PresentationGoal {
        return PresentationGoal(
            id = domainGoal.id,
            name = domainGoal.name,
            amount = domainGoal.targetAmount,
            endDate = domainGoal.deadline
        )
    }
}