package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.FinancialGoalRepository
import com.example.t_bank.data.local.entity.FinancialGoalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveFinancialGoalUseCase @Inject constructor(
    private val financialGoalRepository: FinancialGoalRepository
){
    suspend operator fun invoke(goal: FinancialGoalEntity): Flow<Result<Unit>> = flow {
        try {
            financialGoalRepository.saveGoal(goal)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}