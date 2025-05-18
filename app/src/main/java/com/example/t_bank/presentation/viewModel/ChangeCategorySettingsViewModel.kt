package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.domain.usecase.GetCategoriesUseCase
import com.example.t_bank.mapper.CategoryUiMapper
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeCategorySettingsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoryUiMapper: CategoryUiMapper
) : ViewModel() {

    private val _colors = MutableStateFlow<List<Int>>(emptyList())
    val colors: StateFlow<List<Int>> = _colors

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    init {
        loadColors()
    }

    private fun loadColors() {
        viewModelScope.launch {
            _colors.value = availableColors
        }
    }

    fun loadCategory(categoryName: String) {
        viewModelScope.launch {
            val categories = getCategoriesUseCase()
            val selectedCategory = categories.find { it.name == categoryName }
            _selectedCategory.value = selectedCategory?.let { categoryUiMapper.toUiModel(it) }
        }
    }

    fun updateCategoryColor(updatedCategory: Category) {
        _selectedCategory.value = updatedCategory
    }

    companion object {
        private val availableColors = listOf(
            R.color.red,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.blue,
            R.color.purple,
            R.color.pink,
            R.color.brown,
            R.color.black,
            R.color.gray
        )
    }
}