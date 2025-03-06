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

    fun onUserChanged(user: String) {
        _uiState.value = LoginUiState.UserChanged(user)
    }


    fun onPasswordChanged(password: String) {
        _uiState.value = LoginUiState.PasswordChanged(password)
    }

    fun login(user: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val credentials = UserCredentials(user, password)
            val success = repository.authenticate(credentials)
            if (success) {
                _uiState.value = LoginUiState.Success
            } else {
                _uiState.value = LoginUiState.Error("Invalid username or password")
            }
        }
    }
}

sealed class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class UserChanged(val user: String) : LoginUiState()
    data class PasswordChanged(val password: String) : LoginUiState()
}