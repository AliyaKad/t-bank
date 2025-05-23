package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Notification
import com.example.t_bank.data.remote.api.SmartBudgetApiService

class NotificationDataSource(private val apiService: SmartBudgetApiService) {

    suspend fun getNotifications(userId: Int): List<Notification>? {
        val response = apiService.getNotifications(userId)
        return if (response.isSuccessful) response.body() else null
    }
}
