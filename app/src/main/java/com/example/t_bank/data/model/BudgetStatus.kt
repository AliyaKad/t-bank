package com.example.t_bank.data.model

data class BudgetStatus(
    val income: Double,
    val categories: List<BudgetCategoryStatus>
)
