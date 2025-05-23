package com.example.t_bank.data.model

data class BudgetCategoryStatus(
    val name: String,
    val spent: Double,
    val remaining: Double,
    val limit: Double
)
