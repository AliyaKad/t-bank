package com.example.t_bank.domain.usecase.model

import com.example.t_bank.presentation.model.Category

data class BudgetWithCategories(
    val totalBudget: Float,
    val categories: List<Category>
)