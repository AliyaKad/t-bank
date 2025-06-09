package com.example.t_bank.data.repository

import com.example.t_bank.data.remote.datasource.TransactionDataSource
import com.example.t_bank.domain.repository.TransactionRepository
import com.example.t_bank.domain.usecase.model.Transaction
import javax.inject.Inject


class TransactionRepositoryImpl @Inject constructor(
    private val dataSource: TransactionDataSource
) : TransactionRepository {
    override suspend fun getTransactions(userId: Int): List<Transaction> {
        val transactions = dataSource.getTransactions(userId)
        return transactions!!.map { it.toDomain() }
    }
}

private fun com.example.t_bank.data.model.Transaction.toDomain() =
    Transaction(
        date = date,
        description = description,
        amount = amount.toInt(),
        category = if (category.isBlank()) null else category
    )
