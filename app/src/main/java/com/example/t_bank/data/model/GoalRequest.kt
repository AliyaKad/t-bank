package com.example.t_bank.data.model

data class GoalRequest(
    val userId: Int,
    val name: String,
    val targetAmount: Double,
    val deadline: String
)
