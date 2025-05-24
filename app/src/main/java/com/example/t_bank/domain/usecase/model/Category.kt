package com.example.t_bank.domain.usecase.model

data class Category(
    val name: String,
    val iconResId: Int,
    var colorResId: Int,
    var percentage: Float
)
