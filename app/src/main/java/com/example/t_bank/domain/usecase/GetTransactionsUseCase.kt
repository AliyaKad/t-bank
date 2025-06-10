package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.TransactionRepository
import com.example.t_bank.domain.usecase.model.Transaction
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: Int): List<Transaction> {
        return repository.getTransactions(userId)
    }
}
