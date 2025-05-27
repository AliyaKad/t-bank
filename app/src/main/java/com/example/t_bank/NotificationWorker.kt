package com.example.t_bank

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.t_bank.data.remote.datasource.NotificationDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val params: WorkerParameters,
    private val dataSource: NotificationDataSource,
    private val notificationManager: AppNotificationManager
) : CoroutineWorker(context, params) {

    companion object {
        const val USER_ID_KEY = "USER_ID"
    }

    override suspend fun doWork(): Result {
        val userId = inputData.getInt(USER_ID_KEY, -1)
        if (userId == -1) return Result.failure()

        val notifications = dataSource.getNotifications(userId)
        notifications?.forEach { notification ->
            notificationManager.showNotification(notification)
        }

        return Result.success()
    }
}