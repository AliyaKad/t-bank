package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.UpdateCategoryUseCase
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PercentageDistributionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val savedCategories = getCategoriesUseCase().map { it.toDomainModel() }
            _categories.value = savedCategories
        }
    }
    fun updateCategory(updatedCategory: Category) {
        viewModelScope.launch {
            val currentCategories = _categories.value.toMutableList()
            val index = currentCategories.indexOfFirst { it.name == updatedCategory.name }
            if (index != -1) {
                currentCategories[index] = updatedCategory

                if (validatePercentages(currentCategories)) {
                    _categories.value = currentCategories
                    updateCategoryUseCase(updatedCategory.toEntity())
                } else {
                    throw IllegalArgumentException("Сумма процентов должна быть равна 100%")
                }
            }
        }
    }

    private fun validatePercentages(categories: List<Category>): Boolean {
        val totalPercentage = categories.sumOf { it.percentage.toDouble() }
        return totalPercentage == 100.0
    }
}