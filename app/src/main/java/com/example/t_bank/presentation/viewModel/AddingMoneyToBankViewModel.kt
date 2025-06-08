package com.example.t_bank.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddingMoneyToBankViewModel @Inject constructor() : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _errorResId = MutableLiveData<Int?>()
    val errorResId: LiveData<Int?> = _errorResId

    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun onAddAmountClicked() {
        if (_amount.value.isNotEmpty()) {
            viewModelScope.launch {
            }
        } else {
            _errorResId.value = R.string.enter_amount
        }
    }
    fun onErrorMessageShown() {
        _errorResId.value = null
    }
}