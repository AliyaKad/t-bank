package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddingMoneyToBankViewModel : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun onAddAmountClicked() {
        if (_amount.value.isNotEmpty()) {
            viewModelScope.launch {

            }
        } else {
            _error.value = "Введите сумму"
        }
    }
}