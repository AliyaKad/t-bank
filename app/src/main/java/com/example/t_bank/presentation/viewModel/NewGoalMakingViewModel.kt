package com.example.t_bank.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.model.CreateGoalParams
import com.example.t_bank.domain.usecase.CreateGoalUseCase
import com.example.t_bank.Result
import com.example.t_bank.domain.usecase.UpdateGoalUseCase
import com.example.t_bank.mapper.GoalMapper
import com.example.t_bank.presentation.model.Goal
import com.example.t_bank.presentation.model.UiGoalParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGoalMakingViewModel @Inject constructor(
    private val createGoalUseCase: CreateGoalUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _createGoalResult = MutableLiveData<Result<Unit>>()
    val createGoalResult: LiveData<Result<Unit>> = _createGoalResult

    private val _updateGoalResult = MutableLiveData<Result<Unit>>()
    val updateGoalResult: LiveData<Result<Unit>> get() = _updateGoalResult

    val userId = sharedPreferences.getInt("user_id", -1)

    fun createGoal(name: String, amount: Double, deadline: String) {
        viewModelScope.launch {
            val uiParams = UiGoalParams(
                name = name,
                amount = amount,
                deadline = deadline,
                userId = userId
            )

            val domainParams = uiParams.toDomain()

            val result = createGoalUseCase(domainParams)
            _createGoalResult.value = result
        }
    }

    fun updateGoal(goalId: Int, name: String, targetAmount: Double, endDate: String) {
        viewModelScope.launch {
            try {
                val presentationGoal = Goal(
                    id = goalId,
                    name = name,
                    amount = targetAmount,
                    endDate = endDate
                )

                val domainGoal = GoalMapper.mapToDomain(presentationGoal)

                updateGoalUseCase(userId, goalId, domainGoal)
                _updateGoalResult.postValue(Result.Success(Unit))
            } catch (e: Exception) {
                _updateGoalResult.postValue(Result.Failure(e))
            }
        }
    }

    private fun UiGoalParams.toDomain(): CreateGoalParams {
        return CreateGoalParams(
            userId = this.userId,
            name = this.name,
            targetAmount = this.amount,
            deadline = this.deadline
        )
    }
}
