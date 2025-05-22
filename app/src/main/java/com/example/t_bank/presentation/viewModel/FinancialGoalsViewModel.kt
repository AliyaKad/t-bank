package com.example.t_bank.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.domain.usecase.GetGoalsUseCase
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.presentation.model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialGoalsViewModel @Inject constructor(
    application: Application,
    private val getGoalsUseCase: GetGoalsUseCase
) : AndroidViewModel(application) {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals

    fun loadGoals(userId: Int) {
        viewModelScope.launch {
            try {
                val domainGoals = getGoalsUseCase(userId)
                val uiGoals = mapToUiGoals(domainGoals)
                _goals.value = uiGoals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun mapToUiGoals(domainGoals: List<DomainGoal>): List<Goal> {
        return domainGoals.map { domainGoal ->
            Goal(
                name = domainGoal.name,
                amount = getApplication<Application>().getString(
                    R.string.amount_format_float,
                    domainGoal.targetAmount
                ),
                endDate = domainGoal.deadline,
                isAchieved = domainGoal.status == DomainGoal.Status.COMPLETED
            )
        }
    }
}
