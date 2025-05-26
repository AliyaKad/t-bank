package com.example.t_bank.data.model

data class Transaction(
    val userId: Int,
    val date: String,
    val description: String,
    val amount: Double,
    val category: String
)
