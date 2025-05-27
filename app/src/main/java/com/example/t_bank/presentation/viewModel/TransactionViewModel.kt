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
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val domainList = getTransactionsUseCase.invoke(userId)
                val presentationList = domainList.map {
                    Transaction(
                        date = it.date,
                        description = it.description,
                        amount = it.amount,
                        category = it.category
                    )
                }
                _transactions.postValue(presentationList)
                updateFilteredLists()
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Ошибка загрузки транзакций", e)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getCategoriesUseCase.invoke(userId)
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

        _transactions.postValue(updatedList)
        updateFilteredLists()
    }

    private fun updateFilteredLists() {
        val list = _transactions.value ?: emptyList()

        _unallocatedTransactions.postValue(list.filter { it.category.isNullOrBlank() })
        _allocatedTransactions.postValue(list.filter { !it.category.isNullOrBlank() })
    }
}