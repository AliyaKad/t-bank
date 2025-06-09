package com.example.t_bank.domain.usecase.model

data class BudgetStatus(
    val income: Double,
    val categories: List<BudgetCategoryStatus>
)