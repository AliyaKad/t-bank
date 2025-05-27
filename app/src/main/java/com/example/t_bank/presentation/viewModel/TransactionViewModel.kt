package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.t_bank.presentation.model.Transaction

class TransactionViewModel : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>(generateSampleTransactions())
    val transactions: LiveData<List<Transaction>> = _transactions

    private val _unallocatedTransactions = MutableLiveData<List<Transaction>>()
    val unallocatedTransactions: LiveData<List<Transaction>> get() = _unallocatedTransactions

    private val _allocatedTransactions = MutableLiveData<List<Transaction>>()
    val allocatedTransactions: LiveData<List<Transaction>> get() = _allocatedTransactions

    init {
        updateFilteredLists()
    }

    fun assignCategoryToTransaction(transaction: Transaction, categoryId: Int) {
        val currentList = _transactions.value ?: return
        val updatedList = currentList.map { t ->
            if (t.description == transaction.description && t.date == transaction.date) {
                t.copy(categoryId = categoryId)
            } else {
                t
            }
        }
        _transactions.postValue(updatedList)
    }

    private fun updateFilteredLists() {
        val list = _transactions.value ?: emptyList()

        _unallocatedTransactions.postValue(list.filter { it.categoryId == null })
        _allocatedTransactions.postValue(list.filter { it.categoryId != null })
    }

    private fun generateSampleTransactions(): List<Transaction> {
        return listOf(
            Transaction("2025-04-01", "Супермаркет", 1500),
            Transaction("2025-04-02", "Проезд", 300),
            Transaction("2025-04-03", "Ресторан", 800),
            Transaction("2025-04-04", "Коммунальные услуги", 3000, categoryId = 3)
        )
    }
}