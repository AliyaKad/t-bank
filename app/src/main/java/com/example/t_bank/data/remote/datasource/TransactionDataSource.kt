package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Transaction

interface TransactionDataSource {
    suspend fun getTransactions(userId: Int): List<Transaction>?
    suspend fun updateTransactionCategory(userId: Int, transactionId: Int, category: String)
    suspend fun importTransactions(transactions: List<Transaction>)

}
