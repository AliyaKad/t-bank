package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.AuthRepository
import com.example.t_bank.domain.usecase.model.AuthModel
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String): AuthModel {
        return authRepository.refreshToken(refreshToken)
    }
}
