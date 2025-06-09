package com.example.t_bank.presentation.model

data class Goal(
    val id: Int,
    val name: String,
    val amount: Double,
    val saved: Double = 0.0,
    val endDate: String,
    val isAchieved: Boolean = false,
    val progress: Int = 0
)
