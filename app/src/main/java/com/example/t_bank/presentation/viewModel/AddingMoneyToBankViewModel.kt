package com.example.t_bank.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.domain.usecase.AddAmountToGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddingMoneyToBankViewModel @Inject constructor(
    private val addAmountToGoalUseCase: AddAmountToGoalUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _errorResId = MutableLiveData<Int?>()
    val errorResId: LiveData<Int?> = _errorResId

    val userId = sharedPreferences.getLong("user_id", -1).toInt()


    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun onAddAmountClicked(goalId: Int) {
        val input = _amount.value
        if (input.isNotBlank()) {
            val amountToAdd = input.toDoubleOrNull()
            if (amountToAdd != null && amountToAdd > 0) {
                viewModelScope.launch {
                    addAmountToGoalUseCase(userId, goalId, amountToAdd)
                }
            } else {
                _errorResId.value = R.string.enter_valid_amount
            }
        } else {
            _errorResId.value = R.string.enter_amount
        }
    }

    fun onErrorMessageShown() {
        _errorResId.value = null
    }
}
