package com.example.t_bank.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.data.repository.AllBudgetsRepository
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import com.example.t_bank.presentation.model.CategoryForMonths
import com.example.t_bank.presentation.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpendingsHistoryViewModel @Inject constructor(
    private val allBudgetsRepository: AllBudgetsRepository
) : ViewModel() {

    private val _budgetsForAllMonths = MutableLiveData<List<BudgetForAllMonths>>()
    val budgetsForAllMonths: LiveData<List<BudgetForAllMonths>> = _budgetsForAllMonths

    init {
        loadAllBudgets()
    }

    private fun loadAllBudgets() {
        viewModelScope.launch {
            try {
                val allBudgets = allBudgetsRepository.getAllBudgets()
                Log.d("SpendingsHistoryVM", "Loaded ${allBudgets.size} budgets from repository.")
                allBudgets.forEach { budget ->
                    Log.d(
                        "SpendingsHistoryVM",
                        "Budget: month=${budget.month}, totalBudget=${budget.totalBudget}, categories=${budget.categories.size}"
                    )
                    budget.categories.forEach { category ->
                        Log.d(
                            "SpendingsHistoryVM",
                            "Category: name=${category.name}, budget=${category.budget}, spent=${category.amountSpent}"
                        )
                    }
                }
                _budgetsForAllMonths.value = allBudgets
            } catch (e: Exception) {
                Log.e("SpendingsHistoryVM", "Error loading budgets", e)
            }
        }
    }

    fun getCategoryData(categories: List<CategoryForMonths>): List<CategoryForMonths> {
        val totalSpent = categories.sumOf { it.amountSpent.toDouble() }
        Log.d("SpendingsHistoryVM", "Total spent for categories: $totalSpent")
        val updatedCategories = categories.map { category ->
            category.copy(percentage = (category.amountSpent / totalSpent) * 100)
        }
        updatedCategories.forEach { category ->
            Log.d(
                "SpendingsHistoryVM",
                "Updated category: name=${category.name}, percentage=${category.percentage}"
            )
        }
        return updatedCategories
    }

    fun getSpendingData(categories: List<CategoryForMonths>): List<Expense> {
        val expenses = categories.map { category ->
            Expense(
                category = category.name,
                totalAmount = category.budget,
                spentAmount = category.amountSpent,
                colorResId = category.colorResId
            )
        }
        expenses.forEach { expense ->
            Log.d(
                "SpendingsHistoryVM",
                "Expense: category=${expense.category}, total=${expense.totalAmount}, spent=${expense.spentAmount}"
            )
        }
        return expenses
    }
}
