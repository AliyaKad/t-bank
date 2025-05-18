package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import com.example.t_bank.data.repository.SettingsRepository
import javax.inject.Inject

//class GetMonthlyBudgetUseCase @Inject constructor(
//    private val repository: SettingsRepository
//) {
//    suspend operator fun invoke(month: String): MonthlyBudgetEntity? {
//        return repository.getMonthlyBudget(month)
//    }
//}