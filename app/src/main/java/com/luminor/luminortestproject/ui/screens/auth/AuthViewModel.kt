package com.luminor.luminortestproject.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luminor.luminortestproject.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onResult: (AuthResult) -> Unit) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            onResult(
                result.fold(
                    onSuccess = { AuthResult.Success("Logged in") },
                    onFailure = { AuthResult.Error(it.message ?: "Server error") }
                )
            )
        }
    }

    fun register(email: String, password: String, onResult: (AuthResult) -> Unit) {
        viewModelScope.launch {
            val result = repository.register(email, password)
            onResult(
                result.fold(
                    onSuccess = { AuthResult.Success("Registered successfully") },
                    onFailure = { AuthResult.Error(it.message ?: "Server error") }
                )
            )
        }
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository) as T
        }
    }
}
