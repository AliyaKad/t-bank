package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.dao.BudgetWithCategories
import com.example.t_bank.data.repository.MonthlyBudgetRepository
import javax.inject.Inject

class GetBudgetAndCategoriesUseCase @Inject constructor(
    private val repository: MonthlyBudgetRepository
) {
    suspend operator fun invoke(month: String): BudgetWithCategories? {
        return repository.getBudgetAndCategoriesByMonth(month)
    }
}