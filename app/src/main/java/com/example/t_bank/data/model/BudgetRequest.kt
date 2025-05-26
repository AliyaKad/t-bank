package com.example.t_bank.data.model

data class BudgetRequest(
    val userId: Int,
    val income: Double,
    val categories: List<BudgetCategory>
)


