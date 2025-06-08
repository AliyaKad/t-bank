package com.example.t_bank.mapper

import com.example.t_bank.data.model.AuthResponse
import com.example.t_bank.domain.usecase.model.AuthModel
import javax.inject.Inject

class AuthMapper @Inject constructor() {

    fun mapToDomain(authResponse: AuthResponse): AuthModel {
        return AuthModel(
            userId = authResponse.userId,
            accessToken = authResponse.jwtTokenPairDto.accessToken,
            refreshToken = authResponse.jwtTokenPairDto.refreshToken
        )
    }
}
