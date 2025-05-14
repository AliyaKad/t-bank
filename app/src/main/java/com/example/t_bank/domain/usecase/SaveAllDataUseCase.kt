package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.repository.SettingsRepository
import javax.inject.Inject

class SaveAllDataUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(month: String, totalBudget: Float, categories: List<CategoryEntity>) {
        repository.saveMonthlyBudget(month, totalBudget, categories)
    }
}