package com.example.t_bank.data.repository

import com.example.t_bank.data.model.Transaction
import com.example.t_bank.data.remote.datasource.TransactionDataSource
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val dataSource: TransactionDataSource
) {
    suspend fun getTransactions(userId: Int): List<Transaction>? {
        return dataSource.getTransactions(userId)
    }

    suspend fun updateTransactionCategory(userId: Int, transactionId: Int, category: String) {
        dataSource.updateTransactionCategory(userId, transactionId, category)
    }
}