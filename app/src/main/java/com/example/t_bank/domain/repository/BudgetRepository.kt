package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.BudgetStatus

interface BudgetRepository {
    suspend fun getBudgetStatusWithColors(userId: Long): BudgetStatus
}
