package com.example.t_bank.domain.usecase.model

data class Transaction(
    val date: String,
    val description: String,
    val amount: Int,
    var category: String? = null
)