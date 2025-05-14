package com.example.t_bank

import android.app.Application
import android.util.Log
import com.example.t_bank.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
        Log.d("MyApplication", "Application started. Database initialized.")
    }
}