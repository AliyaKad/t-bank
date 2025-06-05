package com.example.t_bank.domain.usecase.model

data class AuthModel(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String
)
