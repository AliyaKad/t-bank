package com.example.t_bank.presentation.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.R
import com.example.t_bank.ResourceProvider
import com.example.t_bank.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginResult>()
    val loginState: LiveData<LoginResult> get() = _loginState

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginResult.Loading
                val result = loginUseCase(phoneNumber, password)
                _loginState.value = LoginResult.Success(resourceProvider.getString(R.string.login_success))
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error(resourceProvider.getString(R.string.login_error))
            }
        }
    }

    sealed class LoginResult {
        object Loading : LoginResult()
        data class Success(val message: String) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}
