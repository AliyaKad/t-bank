package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.data.repository.FinancialGoalRepository
import com.example.t_bank.data.local.entity.FinancialGoalEntity
import com.example.t_bank.domain.usecase.SaveFinancialGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGoalMakingViewModel @Inject constructor(
    private val saveFinancialGoalUseCase: SaveFinancialGoalUseCase
) : ViewModel() {

    fun saveFinancialGoal(goalName: String, amount: Float, endDate: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val financialGoal = FinancialGoalEntity(
                goalName = goalName,
                amount = amount,
                endDate = endDate
            )
            saveFinancialGoalUseCase(financialGoal).collect { result ->
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    onError()
                }
            }
        }
    }
}
