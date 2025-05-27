package com.example.t_bank.data.remote.datasource

import android.content.Context
import com.example.t_bank.data.model.Notification
import com.example.t_bank.data.remote.api.NotificationApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class NotificationDataSourceImpl(private val apiService: NotificationApiService) : NotificationDataSource {
//
//    override suspend fun getNotifications(userId: Int): List<Notification>? {
//        val response = apiService.getNotifications(userId)
//        return if (response.isSuccessful) response.body() else null
//    }
//}
class NotificationDataSourceImpl(
    private val context: Context,
    private val apiService: NotificationApiService
) : NotificationDataSource {

    private val TAG = "NotificationDataSource"

    override suspend fun getNotifications(userId: Int): List<Notification>? {
        return try {
            val json = withContext(Dispatchers.IO) {
                context.assets.open("notifications.json")
                    .bufferedReader()
                    .use { it.readText() }
            }

            val typeToken = object : TypeToken<List<Notification>>() {}.type
            val notifications: List<Notification> = Gson().fromJson(json, typeToken)

            notifications
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
