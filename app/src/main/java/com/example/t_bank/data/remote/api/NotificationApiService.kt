package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.Notification
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApiService {

    @GET("/api/v1/notifications/{userId}")
    suspend fun getNotifications(@Path("userId") userId: Int): Response<List<Notification>>
}
