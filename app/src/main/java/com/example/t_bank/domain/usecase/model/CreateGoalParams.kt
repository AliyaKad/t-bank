package com.example.t_bank.domain.usecase.model

data class CreateGoalParams(
    val userId: Int,
    val name: String,
    val targetAmount: Double,
    val deadline: String
)
