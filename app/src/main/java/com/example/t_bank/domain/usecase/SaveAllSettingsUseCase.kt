package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.domain.usecase.model.Category
import javax.inject.Inject

class SaveAllSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(
        userId: Int,
        month: String,
        totalBudget: Float,
        categories: List<Category>
    ) {
        repository.saveMonthlyBudget(userId, month, totalBudget, categories)
    }
}
