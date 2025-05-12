package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.GetMonthlyBudgetUseCase
import com.example.t_bank.presentation.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DistributionOfFinancesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMonthlyBudgetUseCase: GetMonthlyBudgetUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _totalBudget = MutableStateFlow<Float>(0f)
    val totalBudget: StateFlow<Float> = _totalBudget

    init {
        loadCategoriesAndBudget()
    }

    private fun loadCategoriesAndBudget() {
        viewModelScope.launch {
            val savedCategories = getCategoriesUseCase().map { it.toDomainModel() }
            _categories.value = savedCategories

            val budget = getMonthlyBudgetUseCase("2023-10")?.totalBudget ?: 0f
            _totalBudget.value = budget
        }
    }
}