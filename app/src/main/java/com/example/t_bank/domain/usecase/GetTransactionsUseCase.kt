package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.TransactionRepository
import com.example.t_bank.domain.usecase.model.Transaction
import com.example.t_bank.mapper.TransactionMapper
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(userId: Int): List<Transaction> {
        val transactions = repository.getTransactions(userId)
        return TransactionMapper.mapListFromData(transactions)
    }
}