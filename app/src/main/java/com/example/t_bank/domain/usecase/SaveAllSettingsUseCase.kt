package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.SettingsRepository
import com.example.t_bank.domain.usecase.model.Category
import javax.inject.Inject

class SaveAllSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(
        userId: Long,
        month: String,
        totalBudget: Float,
        categories: List<Category>
    ) {
        repository.saveMonthlyBudget(userId, month, totalBudget, categories)
    }
}

