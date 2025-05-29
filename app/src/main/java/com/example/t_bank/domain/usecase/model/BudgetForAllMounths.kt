package com.example.t_bank.domain.usecase.model

import com.example.t_bank.presentation.model.CategoryForMonths

data class BudgetForAllMonths (
    val totalBudget: Float,
    val month: String,
    val categories: List<CategoryForMonths>
)
