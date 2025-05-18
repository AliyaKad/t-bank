package com.example.t_bank.data.local.dao

import androidx.room.*
import com.example.t_bank.data.local.entity.FinancialGoalEntity

@Dao
interface FinancialGoalDao {

    @Insert
    suspend fun insertGoal(goal: FinancialGoalEntity): Long

    @Query("SELECT * FROM financial_goals")
    suspend fun getAllGoals(): List<FinancialGoalEntity>

    @Query("DELETE FROM financial_goals WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: Int)

    @Update
    suspend fun updateGoal(goal: FinancialGoalEntity)
}