package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.usecase.CreateGoalParams
import com.example.t_bank.domain.usecase.CreateGoalUseCase
import com.example.t_bank.Result
import com.example.t_bank.presentation.model.UiGoalParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGoalMakingViewModel @Inject constructor(
    private val createGoalUseCase: CreateGoalUseCase
) : ViewModel() {

    private val _createGoalResult = MutableLiveData<Result<Unit>>()
    val createGoalResult: LiveData<Result<Unit>> = _createGoalResult

    fun createGoal(name: String, amount: Double, deadline: String, userId: Int) {
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

    private fun UiGoalParams.toDomain(): CreateGoalParams {
        return CreateGoalParams(
            userId = this.userId,
            name = this.name,
            targetAmount = this.amount,
            deadline = this.deadline
        )
    }
}