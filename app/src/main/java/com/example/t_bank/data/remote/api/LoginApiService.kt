package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.AuthRequest
import com.example.t_bank.data.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("/api/v1/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @GET("/api/v1/refresh")
    suspend fun refreshToken(): Response<AuthResponse>
}
