package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstSettingsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val savedCategories = getCategoriesUseCase()
            _categories.value = savedCategories
        }
    }

    fun updateCategory(updatedCategory: Category) {
        val currentCategories = _categories.value.toMutableList()
        val index = currentCategories.indexOfFirst { it.name == updatedCategory.name }
        if (index != -1) {
            currentCategories[index] = updatedCategory
            _categories.value = currentCategories
        }
    }
}