package com.example.t_bank.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.t_bank.data.remote.datasource.LoginDataSource
import com.example.t_bank.domain.usecase.model.AuthModel
import com.example.t_bank.mapper.AuthMapper
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource,
    private val sharedPreferences: SharedPreferences,
    private val authMapper: AuthMapper
) : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String): AuthModel {
        val response = dataSource.login(phoneNumber, password)

        with(sharedPreferences.edit()) {
            putLong("user_id", response.userId.toLong())
            putString("access_token", response.jwtTokenPairDto.accessToken)
            putString("refresh_token", response.jwtTokenPairDto.refreshToken)
            apply()
        }

        Log.d("AuthRepository", "Saved user_id: ${response.userId}")
        Log.d("AuthRepository", "Saved access_token: ${response.jwtTokenPairDto.accessToken}")
        Log.d("AuthRepository", "Saved refresh_token: ${response.jwtTokenPairDto.refreshToken}")

        return authMapper.mapToDomain(response)
    }

    override suspend fun refreshToken(refreshToken: String): AuthModel {
        val response = dataSource.refreshToken(refreshToken)

        with(sharedPreferences.edit()) {
            putString("access_token", response.jwtTokenPairDto.accessToken)
            putString("refresh_token", response.jwtTokenPairDto.accessToken)
            apply()
        }

        return authMapper.mapToDomain(response)
    }
}
