package com.example.t_bank.presentation.model

data class Transaction(
    val date: String,
    val description: String,
    val amount: Int,
    var categoryId: Int? = null,
    var isExpanded: Boolean = false
)