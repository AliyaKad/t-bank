package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.AuthRepository
import com.example.t_bank.domain.usecase.model.AuthModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String, password: String): AuthModel {
        return authRepository.login(phoneNumber, password)
    }
}
