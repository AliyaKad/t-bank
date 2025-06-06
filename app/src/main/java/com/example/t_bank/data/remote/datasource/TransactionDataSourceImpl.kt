package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.Transaction
import com.example.t_bank.data.remote.api.TransactionApiService

class TransactionDataSourceImpl(private val apiService: TransactionApiService) : TransactionDataSource {

    override suspend fun getTransactions(userId: Int): List<Transaction>? {
        val response = apiService.getTransactions(userId)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateTransactionCategory(userId: Int, transactionId: Int, category: String) {
        val categoryUpdate = mapOf("category" to category)
        apiService.updateTransactionCategory(userId, transactionId, categoryUpdate)
    }

    override suspend fun importTransactions(transactions: List<Transaction>) {
        apiService.importTransactions(transactions)
    }
}
