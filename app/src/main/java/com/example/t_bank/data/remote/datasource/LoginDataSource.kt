package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.AuthResponse

interface LoginDataSource {
    suspend fun login(phoneNumber: String, password: String): AuthResponse

    suspend fun refreshToken(refreshToken: String): AuthResponse
}
