package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions(userId: Int): List<Transaction>
}
