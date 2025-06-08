package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.t_bank.R
import com.example.t_bank.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor() : ViewModel() {
    
    private val _selectedColor = MutableStateFlow<Int?>(null)
    val selectedColor: StateFlow<Int?> = _selectedColor

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName

    fun setSelectedColor(colorResId: Int) {
        _selectedColor.value = colorResId
    }

    fun setCategoryName(name: String) {
        _categoryName.value = name
    }

    fun createCategory(): Category? {
        val name = _categoryName.value.trim()
        val color = _selectedColor.value ?: return null
        return Category(
            name = name,
            colorResId = color,
            iconResId = R.drawable.ic_new_category,
            percentage = 0f
        )
    }
}