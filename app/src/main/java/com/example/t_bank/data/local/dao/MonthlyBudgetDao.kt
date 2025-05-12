package com.example.t_bank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity

@Dao
interface MonthlyBudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyBudget(budget: MonthlyBudgetEntity)

    @Query("SELECT * FROM monthly_budgets WHERE month = :month")
    suspend fun getMonthlyBudget(month: String): MonthlyBudgetEntity?

    @Query("DELETE FROM monthly_budgets WHERE month = :month")
    suspend fun deleteMonthlyBudget(month: String)
}