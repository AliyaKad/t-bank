package com.example.t_bank.domain.usecase

import com.example.t_bank.Result
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.repository.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {

    suspend operator fun invoke(params: CreateGoalParams): Result<Unit> {
        val goalRequest = params.toGoalRequest()
        return goalRepository.createGoal(goalRequest)
    }
}

fun CreateGoalParams.toGoalRequest(): GoalRequest {
    return GoalRequest(
        userId = this.userId,
        name = this.name,
        targetAmount = this.targetAmount,
        deadline = this.deadline
    )
}

data class CreateGoalParams(
    val userId: Int,
    val name: String,
    val targetAmount: Double,
    val deadline: String
)
