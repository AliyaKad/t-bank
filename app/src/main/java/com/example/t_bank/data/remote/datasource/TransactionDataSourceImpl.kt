package com.example.t_bank.data.remote.datasource

import android.content.Context
import com.example.t_bank.data.model.Transaction
import com.example.t_bank.data.remote.api.TransactionApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TransactionDataSourceImpl(private val apiService: TransactionApiService, @ApplicationContext private val context: Context) : TransactionDataSource {

//    override suspend fun getTransactions(userId: Int): List<Transaction>? {
//        val response = apiService.getTransactions(userId)
//        return if (response.isSuccessful) response.body() else null
//    }

    override suspend fun getTransactions(userId: Int): List<Transaction> {
        val json = withContext(Dispatchers.IO) {
            context.assets.open("transactions.json").bufferedReader().use { it.readText() }
        }

        val typeToken = object : TypeToken<List<Transaction>>() {}.type
        return Gson().fromJson(json, typeToken) ?: emptyList()
    }

    override suspend fun updateTransactionCategory(userId: Int, transactionId: Int, category: String) {
        val categoryUpdate = mapOf("category" to category)
        apiService.updateTransactionCategory(userId, transactionId, categoryUpdate)
    }

    override suspend fun importTransactions(transactions: List<Transaction>) {
        apiService.importTransactions(transactions)
    }
}
