package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.BudgetRepository
import com.example.t_bank.domain.usecase.model.BudgetStatus
import javax.inject.Inject

class GetBudgetStatusUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository
) {
    suspend operator fun invoke(userId: Long): BudgetStatus {
        return budgetRepository.getBudgetStatusWithColors(userId)
    }
}
