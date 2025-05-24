package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.SaveAllSettingsUseCase
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PercentageDistributionViewModel @Inject constructor(
    private val saveAllDataUseCase: SaveAllSettingsUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun loadCategories(categories: List<Category>) {
        viewModelScope.launch {
            _categories.value = categories
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
                } else {
                    throw IllegalArgumentException("Сумма процентов должна быть равна 100%")
                }
            }
        }
    }

    fun getCategories(): List<Category> {
        return _categories.value
    }

    private fun validatePercentages(categories: List<Category>): Boolean {
        val totalPercentage = categories.sumOf { it.percentage.toDouble() }
        return totalPercentage == 100.0
    }

    fun saveDataToDatabase(month: String, totalBudget: Float) {
        viewModelScope.launch {
            val categories = _categories.value.map { it.toDomainModel() }
            saveAllDataUseCase(1, month, totalBudget, categories)
        }
    }
}