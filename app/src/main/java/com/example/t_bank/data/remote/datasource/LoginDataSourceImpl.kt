package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.AuthRequest
import com.example.t_bank.data.model.AuthResponse
import com.example.t_bank.data.remote.api.LoginApiService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginApiService: LoginApiService
) : LoginDataSource {

    override suspend fun login(phoneNumber: String, password: String): AuthResponse {
        val response = loginApiService.login(AuthRequest(phoneNumber, password))
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Login failed")
        }
    }

    override suspend fun refreshToken(refreshToken: String): AuthResponse {
        val request = mapOf("refreshToken" to refreshToken)
        val response = loginApiService.refreshToken()
        if (!response.isSuccessful || response.body() == null) {
            throw Exception("Не удалось обновить токен")
        }
        return response.body()!!
    }
}
