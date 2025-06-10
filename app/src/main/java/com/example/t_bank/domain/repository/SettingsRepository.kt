package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.Category

interface SettingsRepository {
    suspend fun saveMonthlyBudget(
        userId: Long,
        month: String,
        totalBudget: Float,
        categories: List<Category>
    )
}
