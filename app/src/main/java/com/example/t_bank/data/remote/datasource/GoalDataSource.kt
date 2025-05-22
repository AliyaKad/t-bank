package com.example.t_bank.data.remote.datasource

import android.util.Log
import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.remote.api.SmartBudgetApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import javax.inject.Inject
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext


class GoalDataSource @Inject constructor(
    private val apiService: SmartBudgetApiService,
    @ApplicationContext private val context: Context
) {
    private val TAG = "GoalRepository"

    suspend fun createGoal(goalRequest: GoalRequest) {
        try {
            Log.d(TAG, "Creating goal with request: $goalRequest")
            val response = apiService.createGoal(goalRequest)

            if (response.isSuccessful) {
                Log.d(TAG, "Goal created successfully")
            } else {
                Log.e(TAG, "Failed to create goal. Code: ${response.code()}, Message: ${response.message()}")
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Error body: $errorBody")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while creating goal: ${e.message}")
        }
    }
//api
//    suspend fun getGoals(userId: Int): List<DataGoal>? {
//        val response = apiService.getGoals(userId)
//        println("Fetching goals for userId: $userId")
//        println("Response code: ${response.code()}, message: ${response.message()}")
//        println("Response body: ${response.body()}")
//
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            println("Error: ${response.errorBody()?.string()}")
//            return null
//        }
//    }

    //json
    suspend fun getGoals(userId: Int): List<DataGoal>? {
        return try {
            val json = context.assets.open("goals.json").bufferedReader().use { it.readText() }
            val typeToken = object : TypeToken<List<DataGoal>>() {}.type
            val goals = Gson().fromJson<List<DataGoal>>(json, typeToken)

            Log.d(TAG, "Fetched goals for userId: $userId")
            Log.d(TAG, "Goals: $goals")

            goals
        } catch (e: Exception) {
            Log.e(TAG, "Error loading goals from JSON: ${e.message}")
            null
        }
    }

    suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest) {
        apiService.updateGoal(userId, goalId, goalRequest)
    }

    suspend fun deleteGoal(userId: Int, goalId: Int) {
        apiService.deleteGoal(userId, goalId)
    }
}
