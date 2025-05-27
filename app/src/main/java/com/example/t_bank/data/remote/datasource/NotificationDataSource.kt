package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Notification

interface NotificationDataSource {
    suspend fun getNotifications(userId: Int): List<Notification>?
}
