package com.example.t_bank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_budgets")
data class MonthlyBudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val month: String,
    val totalBudget: Float
)