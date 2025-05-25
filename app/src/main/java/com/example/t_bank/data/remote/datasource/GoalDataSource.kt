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

interface GoalDataSource {
    suspend fun createGoal(goalRequest: GoalRequest)
    suspend fun getGoals(userId: Int): List<DataGoal>?
    suspend fun updateGoal(userId: Int, goalId: Int, goalRequest: GoalRequest)
    suspend fun deleteGoal(userId: Int, goalId: Int)
}

