package com.example.t_bank.presentation.viewModel


import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetBudgetStatusUseCase
import com.example.t_bank.presentation.model.CategoryForMonths
import com.example.t_bank.presentation.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getBudgetStatusUseCase: GetBudgetStatusUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    private val _categoriesForMonths = MutableLiveData<List<CategoryForMonths>>()
    val categoriesForMonths: LiveData<List<CategoryForMonths>> = _categoriesForMonths

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadBudgetStatus() {
        viewModelScope.launch {
            try {
                val userId = getUserId()
                val status = getBudgetStatusUseCase(userId)

                val expenseList = status.categories.map { category ->
                    Expense(
                        category = category.name,
                        totalAmount = category.limit.toFloat(),
                        spentAmount = category.spent.toFloat(),
                        colorResId = category.colorResId
                    )
                }

                val categoryList = status.categories.map { category ->
                    CategoryForMonths(
                        name = category.name,
                        colorResId = category.colorResId,
                        budget = status.income.toFloat(),
                        amountSpent = category.spent.toFloat(),
                        percentage = category.spent / category.limit
                    )
                }

                _expenses.value = expenseList
                _categoriesForMonths.value = categoryList
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun getUserId(): Long {
        val id = sharedPreferences.getLong("user_id", -1)
        Log.d("ExpensesViewModel", "UserId из SharedPreferences: $id")
        return id
    }
}