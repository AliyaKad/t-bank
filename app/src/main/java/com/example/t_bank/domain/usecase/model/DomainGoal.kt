package com.example.t_bank.domain.usecase.model

data class DomainGoal(
    val id: Int,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val recommendedMonthlySaving: Double,
    val deadline: String,
    val status: Status
) {
    enum class Status {
        COMPLETED,
        IN_PROGRESS
    }
}
