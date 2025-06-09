package com.example.t_bank.presentation.viewModel

import android.content.SharedPreferences
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
    private val sharedPreferences: SharedPreferences
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
                    val excess = currentCategories.sumOf { it.percentage.toDouble() } - 100
                    currentCategories[index] = updatedCategory.copy(percentage = updatedCategory.percentage - excess.toFloat())
                    _categories.value = currentCategories
                }
            }
        }
    }

    fun validatePercentages(categories: List<Category>): Boolean {
        val totalPercentage = categories.sumOf { it.percentage.toDouble() }
        return totalPercentage <= 100.0
    }

    fun getCategories(): List<Category> {
        return _categories.value
    }



    fun saveDataToDatabase(month: String, totalBudget: Float) {
        viewModelScope.launch {
            val userId = getUserId()
            val categories = _categories.value.map { it.toDomainModel() }
            saveAllDataUseCase(userId, month, totalBudget, categories)
        }
    }

    private fun getUserId(): Long {
        val userId = sharedPreferences.getLong("user_id", -1L)
        if (userId == -1L) {
            throw IllegalStateException("User ID not found in SharedPreferences")
        }
        return userId
    }
}
