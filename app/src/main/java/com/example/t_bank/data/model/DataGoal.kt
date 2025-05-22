package com.example.t_bank.data.model

data class DataGoal(
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val recommendedMonthlySaving: Double,
    val deadline: String,
    val status: String
)
