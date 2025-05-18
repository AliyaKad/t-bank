package com.example.t_bank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_goals")
data class FinancialGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val goalName: String,
    val amount: Float,
    val endDate: String
)
