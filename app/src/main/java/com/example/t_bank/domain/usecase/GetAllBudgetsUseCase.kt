package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.AllBudgetsRepository
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import javax.inject.Inject

class GetAllBudgetsUseCase @Inject constructor(
    private val repository: AllBudgetsRepository
) {
    suspend operator fun invoke(): List<BudgetForAllMonths> {
        return repository.getAllBudgets()
    }
}
