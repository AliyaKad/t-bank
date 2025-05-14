package com.example.t_bank.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetBudgetAndCategoriesUseCase
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Calendar

@HiltViewModel
class DistributionOfFinancesViewModel @Inject constructor(
    private val getBudgetAndCategoriesUseCase: GetBudgetAndCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _totalBudget = MutableStateFlow<Float>(0f)
    val totalBudget: StateFlow<Float> = _totalBudget

    private val _currentMonth = MutableStateFlow<String>("")
    val currentMonth: StateFlow<String> = _currentMonth

    init {
        loadBudgetAndCategories()
    }

    private fun loadBudgetAndCategories() {
        viewModelScope.launch {
            val month = getCurrentMonth()
            Log.d("DistributionViewModel", "Loading data for month: $month")
            val data = getBudgetAndCategoriesUseCase(month)
            if (data != null) {
                Log.d("DistributionViewModel", "Loaded totalBudget: ${data.totalBudget}")
                Log.d("DistributionViewModel", "Loaded categories: ${data.categories}")
                _totalBudget.value = data.totalBudget
                _categories.value = data.categories
                _currentMonth.value = month
            } else {
                Log.e("DistributionViewModel", "No data found for month: $month")
            }
        }
    }

    private fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        return String.format("%d-%02d", year, month)
    }
}