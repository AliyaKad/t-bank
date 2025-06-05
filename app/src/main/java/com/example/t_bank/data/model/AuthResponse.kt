package com.example.t_bank.data.model

data class AuthResponse(
    val userId: Int,
    val jwtTokenPairDto: JwtTokenPairDto
)

data class JwtTokenPairDto(
    val accessToken: String,
    val refreshToken: String
)