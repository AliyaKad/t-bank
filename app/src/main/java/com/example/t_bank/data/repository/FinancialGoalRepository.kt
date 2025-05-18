package com.example.t_bank.data.repository

import android.util.Log
import com.example.t_bank.data.local.dao.FinancialGoalDao
import com.example.t_bank.data.local.entity.FinancialGoalEntity
import javax.inject.Inject

class FinancialGoalRepository @Inject constructor(
    private val financialGoalDao: FinancialGoalDao
) {
    private val TAG = "FinancialGoalRepository"

    suspend fun saveGoal(goal: FinancialGoalEntity) {
        Log.d(TAG, "Saving financial goal: ${goal.goalName}, Amount: ${goal.amount}, End Date: ${goal.endDate}")
        try {
            financialGoalDao.insertGoal(goal)
            Log.d(TAG, "Financial goal saved successfully: ${goal.goalName}")
        } catch (e: Exception) {
            Log.e(TAG, "Error while saving financial goal: ${goal.goalName}", e)
        }
    }

    suspend fun getAllGoals(): List<FinancialGoalEntity> {
        Log.d(TAG, "Fetching all financial goals from the database")
        return try {
            val goals = financialGoalDao.getAllGoals()
            Log.d(TAG, "Fetched ${goals.size} financial goals")
            goals
        } catch (e: Exception) {
            Log.e(TAG, "Error while fetching financial goals", e)
            emptyList()
        }
    }
}