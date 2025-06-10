package com.example.t_bank.presentation.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.t_bank.domain.usecase.GetTransactionsUseCase
import com.example.t_bank.mapper.TransactionMapper
import com.example.t_bank.presentation.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TransactionsByCategoryViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val userId = getUserId().toInt()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    suspend fun loadTransactionsByCategory(categoryName: String?) {
        val allTransactions = getTransactionsUseCase(userId)
        val filteredDomain = allTransactions.filter { it.category == categoryName }
        val mappedToPresentation = TransactionMapper.mapListToPresentation(filteredDomain)
        _transactions.value = mappedToPresentation
    }

    fun getFilteredTransactions(): List<Transaction> = _transactions.value

    private fun getUserId(): Long {
        val id = sharedPreferences.getLong("user_id", -1)
        Log.d("ExpensesViewModel", "UserId из SharedPreferences: $id")
        return id
    }
}
