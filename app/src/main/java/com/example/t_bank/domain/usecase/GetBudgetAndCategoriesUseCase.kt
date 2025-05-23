package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.MonthlyBudgetRepository
import com.example.t_bank.domain.usecase.model.BudgetWithCategories
import javax.inject.Inject

class GetBudgetAndCategoriesUseCase @Inject constructor(
    private val repository: MonthlyBudgetRepository
) {
    suspend operator fun invoke(month: String): BudgetWithCategories? {
        return repository.getBudgetAndCategoriesByMonth(month)
    }
}