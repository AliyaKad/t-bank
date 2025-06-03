package com.example.t_bank.presentation.model

data class Expense(
    val category: String,
    val totalAmount: Float,
    val spentAmount: Float,
    val colorResId: Int = 0
)

