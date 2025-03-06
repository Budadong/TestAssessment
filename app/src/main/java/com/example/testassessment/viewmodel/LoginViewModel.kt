package com.example.testassessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassessment.model.LoginRepository
import com.example.testassessment.model.UserCredentials
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository = LoginRepository()) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>(LoginUiState.Initial)
    val uiState: LiveData<LoginUiState> = _uiState

    fun onEmailChanged(email: String) {
        _uiState.value = LoginUiState.EmailChanged(email)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = LoginUiState.PasswordChanged(password)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val credentials = UserCredentials(email, password)
            val success = repository.authenticate(credentials)
            if (success) {
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error("Invalid email or password")
            }
        }
    }
}

sealed class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class EmailChanged(val email: String) : LoginUiState()
    data class PasswordChanged(val password: String) : LoginUiState()
}