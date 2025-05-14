package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.entity.CategoryDistributionEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import com.example.t_bank.data.repository.SettingsRepository
import javax.inject.Inject

class GetMonthlyBudgetHistoryUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(month: String): Pair<MonthlyBudgetEntity?, List<CategoryDistributionEntity>> {
        return repository.getMonthlyBudgetWithDistributions(month)
    }
}