package com.example.t_bank.mapper

import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.presentation.model.Goal

object GoalMapper {

    fun mapToData(domainGoal: DomainGoal): GoalRequest {
        return GoalRequest(
            userId = domainGoal.id,
            name = domainGoal.name,
            targetAmount = domainGoal.targetAmount,
            deadline = domainGoal.deadline
        )
    }

    fun mapToDomain(presentationGoal: Goal): DomainGoal {
        return DomainGoal(
            id = presentationGoal.id,
            name = presentationGoal.name,
            targetAmount = presentationGoal.amount,
            savedAmount = 0.0,
            deadline = presentationGoal.endDate,
            status = DomainGoal.Status.COMPLETED,
            recommendedMonthlySaving = 0.0
        )
    }
}
