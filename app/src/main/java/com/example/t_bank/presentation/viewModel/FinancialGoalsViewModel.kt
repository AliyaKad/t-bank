package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.data.local.entity.FinancialGoalEntity
import com.example.t_bank.domain.usecase.GetAllFinancialGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialGoalsViewModel @Inject constructor(
    private val getAllFinancialGoalsUseCase: GetAllFinancialGoalsUseCase
) : ViewModel() {

    private val _goals = MutableStateFlow<List<FinancialGoalEntity>>(emptyList())
    val goals: StateFlow<List<FinancialGoalEntity>> = _goals

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            _goals.value = getAllFinancialGoalsUseCase().toList()
        }
    }
}