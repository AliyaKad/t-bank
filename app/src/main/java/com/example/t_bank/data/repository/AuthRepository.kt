package com.example.t_bank.data.repository

import com.example.t_bank.domain.usecase.model.AuthModel

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String): AuthModel
}