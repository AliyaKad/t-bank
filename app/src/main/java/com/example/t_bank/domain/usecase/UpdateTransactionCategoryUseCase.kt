package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: Int, transactionId: Int, category: String) {
        repository.updateTransactionCategory(userId, transactionId, category)
    }
}