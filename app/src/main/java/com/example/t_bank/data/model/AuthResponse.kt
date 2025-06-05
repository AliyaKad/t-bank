package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("jwtTokenPairDto")
    val jwtTokenPairDto: JwtTokenPairDto
)

data class JwtTokenPairDto(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
