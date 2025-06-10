package com.example.t_bank.data.remote.datasource

import android.content.Context
import android.util.Log
import com.example.t_bank.data.model.AddAmountRequest
import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.remote.api.GoalApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoalDataSourceImpl @Inject constructor(
    private val apiService: GoalApiService,
    @ApplicationContext private val context: Context
) : GoalDataSource {
    private val TAG = "GoalRepository"

    override suspend fun createGoal(goalRequest: GoalRequest) {
        try {
            val response = apiService.createGoal(goalRequest.userId, goalRequest)

            if (response.isSuccessful) {
                Log.d(TAG, "Goal created successfully")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Error body: $errorBody")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while creating goal: ${e.message}")
        }
    }

    override suspend fun getGoals(userId: Int): List<DataGoal>? {
        val response = apiService.getGoals(userId)

        if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    override suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest) {
        apiService.updateGoal(userId, goalId, goalRequest)
    }

    override suspend fun deleteGoal(userId: Int, goalId: Int) {
        apiService.deleteGoal(userId, goalId)
    }

    override suspend fun addAmountToGoal(userId: Int, goalId: Int, amount: Double): DataGoal? {
        val request = AddAmountRequest(amount)
        val response = apiService.addAmountToGoal(userId, goalId, request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}
