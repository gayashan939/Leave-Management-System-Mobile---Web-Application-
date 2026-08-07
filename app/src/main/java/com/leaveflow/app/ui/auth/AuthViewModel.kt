package com.leaveflow.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaveflow.app.data.repository.AuthRepository
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean   = false,
    val isLoggedIn: Boolean  = false,
    val currentUser: User?   = null,
    val errorMessage: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        // Observe session on startup
        viewModelScope.launch {
            authRepository.isLoggedIn.collect { loggedIn ->
                if (loggedIn) {
                    val user = authRepository.getCurrentUserOnce()
                    _uiState.value = _uiState.value.copy(isLoggedIn = true, currentUser = user)
                } else {
                    _uiState.value = _uiState.value.copy(isLoggedIn = false, currentUser = null)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading  = false,
                        isLoggedIn = true,
                        currentUser = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading    = false,
                        errorMessage = result.message
                    )
                }
                else -> {}
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = AuthUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}
