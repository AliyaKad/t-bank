package com.example.t_bank.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetTransactionsUseCase
import com.example.t_bank.domain.usecase.GetCategoriesForTransactionsUseCase
import com.example.t_bank.mapper.CategoryMapper
import com.example.t_bank.presentation.model.CategoryRepository

import com.example.t_bank.presentation.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesForTransactionsUseCase
) : ViewModel() {

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions

    private val _unallocatedTransactions = MutableLiveData<List<Transaction>>()
    val unallocatedTransactions: LiveData<List<Transaction>> get() = _unallocatedTransactions

    private val _allocatedTransactions = MutableLiveData<List<Transaction>>()
    val allocatedTransactions: LiveData<List<Transaction>> get() = _allocatedTransactions

    private var userId = 1

    init {
        loadTransactions()
        loadCategories()


        _transactions.observeForever { transactions ->
            Log.d("TransactionViewModel", "Transactions observed: $transactions")
            updateFilteredLists()
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                Log.d("TransactionViewModel", "Loading transactions...")
                val domainList = getTransactionsUseCase.invoke(userId)
                Log.d("TransactionViewModel", "Loaded transactions: $domainList")

                val presentationList = domainList.map {
                    Transaction(
                        date = it.date,
                        description = it.description,
                        amount = it.amount,
                        category = it.category
                    )
                }
                Log.d("TransactionViewModel", "Mapped transactions: $presentationList")

                _transactions.value = presentationList
                Log.d("TransactionViewModel", "Transactions set to LiveData: $presentationList")

                updateFilteredLists()
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Ошибка загрузки транзакций", e)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                Log.d("TransactionViewModel", "Loading categories...")
                val categories = getCategoriesUseCase.invoke(userId)
                Log.d("TransactionViewModel", "Loaded categories: $categories")
                CategoryRepository.categories = CategoryMapper.mapListFromDomain(categories)
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Ошибка загрузки категорий", e)
            }
        }
    }

    fun assignCategoryToTransaction(transaction: Transaction, newCategory: String) {
        val updatedList = _transactions.value?.map { t ->
            if (t.description == transaction.description && t.date == transaction.date) {
                t.copy(category = newCategory)
            } else {
                t
            }
        } ?: return

        _transactions.value = updatedList
        Log.d("TransactionViewModel", "Updated transactions: $updatedList")
        updateFilteredLists()
    }

    private fun updateFilteredLists() {
        val list = _transactions.value ?: emptyList()
        Log.d("TransactionViewModel", "Original transactions in updateFilteredLists: $list")

        if (list.isEmpty()) {
            Log.w("TransactionViewModel", "No transactions available for filtering.")
            return
        }

        val unallocated = list.filter { it.category.isNullOrEmpty() }
        val allocated = list.filter { !it.category.isNullOrEmpty() }

        Log.d("TransactionViewModel", "Unallocated transactions: $unallocated")
        Log.d("TransactionViewModel", "Allocated transactions: $allocated")

        _unallocatedTransactions.value = unallocated
        _allocatedTransactions.value = allocated
    }
}