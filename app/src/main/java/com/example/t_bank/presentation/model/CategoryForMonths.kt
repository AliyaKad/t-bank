package com.example.t_bank.presentation.model

data class CategoryForMonths(
    val name: String,
    var colorResId: Int,
    var budget: Float,
    var amountSpent: Float,
    var percentage: Double = 0.0
)
