package com.example.t_bank.domain.usecase.model

data class BudgetCategoryStatus (
    val name: String,
    val spent: Double,
    val remaining: Double,
    val limit: Double,
    val colorResId: Int
)