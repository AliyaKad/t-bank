package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.FinancialGoalRepository
import com.example.t_bank.data.local.entity.FinancialGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFinancialGoalsUseCase @Inject constructor(
    private val financialGoalRepository: FinancialGoalRepository
) {
    suspend operator fun invoke(): List<FinancialGoalEntity> {
        return financialGoalRepository.getAllGoals()
    }
}