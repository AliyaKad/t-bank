package com.example.t_bank.presentation.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.domain.usecase.DeleteGoalUseCase
import com.example.t_bank.domain.usecase.GetGoalsUseCase
import com.example.t_bank.domain.usecase.model.DomainGoal
import com.example.t_bank.presentation.model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialGoalsViewModel @Inject constructor(
    private val getGoalsUseCase: GetGoalsUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals

    val userId = sharedPreferences.getInt("user_id", -1)

    fun loadGoals() {
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

    fun deleteGoal(goalId: Int) {
        viewModelScope.launch {
            try {
                deleteGoalUseCase(userId, goalId)
                _goals.update { goals -> goals.filter { it.id != goalId } }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun mapToUiGoals(domainGoals: List<DomainGoal>): List<Goal> {
        return domainGoals.map { domainGoal ->
            val progress = if (domainGoal.targetAmount > 0) {
                ((domainGoal.savedAmount / domainGoal.targetAmount) * 100).toInt()
            } else {
                0
            }
            Goal(
                id = domainGoal.id,
                name = domainGoal.name,
                amount = domainGoal.targetAmount,
                saved = domainGoal.savedAmount,
                endDate = domainGoal.deadline,
                isAchieved = domainGoal.status == DomainGoal.Status.COMPLETED,
                progress = progress
            )
        }
    }
}
