package com.example.t_bank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var notificationManager: AppNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.createNotificationChannel()
    }
}
