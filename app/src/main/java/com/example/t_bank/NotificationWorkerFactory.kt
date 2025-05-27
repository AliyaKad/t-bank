package com.example.t_bank

import android.content.Context

import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.t_bank.data.remote.datasource.NotificationDataSource

class NotificationWorkerFactory(
    private val dataSource: NotificationDataSource,
    private val notificationManager: AppNotificationManager
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        parameters: WorkerParameters
    ): ListenableWorker? {
        return if (workerClassName == NotificationWorker::class.java.name) {
            NotificationWorker(appContext, parameters, dataSource, notificationManager)
        } else {
            null
        }
    }
}
