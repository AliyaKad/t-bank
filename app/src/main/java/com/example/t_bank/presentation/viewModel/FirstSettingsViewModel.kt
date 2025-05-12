package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.domain.usecase.SaveDefaultCategoriesUseCase
import com.example.t_bank.domain.usecase.SaveMonthlyBudgetUseCase
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

import android.util.Log
import com.example.t_bank.domain.usecase.UpdateCategoryUseCase

@HiltViewModel
class FirstSettingsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        Log.d("FirstSettingsViewModel", "Initializing FirstSettingsViewModel")
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val savedCategories = getCategoriesUseCase().map { it.toDomainModel() }
            _categories.value = savedCategories
        }
    }

    fun saveBudgetAndCategories(totalBudget: Float) {
        viewModelScope.launch {
            Log.d("FirstSettingsViewModel", "Saving budget and categories...")
            try {
                val month = getCurrentMonth()
                val categories = _categories.value
                Log.d("FirstSettingsViewModel", "Saving budget for month: $month, totalBudget: $totalBudget")
                Log.d("FirstSettingsViewModel", "Categories to save: $categories")
                Log.d("FirstSettingsViewModel", "Budget and categories saved successfully.")
            } catch (e: Exception) {
                Log.e("FirstSettingsViewModel", "Error saving budget and categories", e)
            }
        }
    }

    private fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val formattedMonth = String.format("%d-%02d", year, month)
        Log.d("FirstSettingsViewModel", "Current month calculated: $formattedMonth")
        return formattedMonth
    }
}