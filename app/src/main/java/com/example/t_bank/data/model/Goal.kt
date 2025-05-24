package com.example.t_bank.data.model

data class Goal(
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val recommendedMonthlySaving: Double,
    val deadline: String,
    val status: String
)
