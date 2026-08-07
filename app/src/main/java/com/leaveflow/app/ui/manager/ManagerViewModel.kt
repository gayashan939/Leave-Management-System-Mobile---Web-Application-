package com.leaveflow.app.ui.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaveflow.app.data.repository.LeaveRepository
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManagerUiState(
    val pendingRequests: List<LeaveRequest> = emptyList(),
    val approvedCount: Int                  = 0,
    val rejectedCount: Int                  = 0,
    val totalCount: Int                     = 0,
    val selectedRequest: LeaveRequest?      = null,
    val isLoading: Boolean                  = false,
    val errorMessage: String                = "",
    val successMessage: String              = ""
)

@HiltViewModel
class ManagerViewModel @Inject constructor(
    private val leaveRepository: LeaveRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManagerUiState())
    val uiState: StateFlow<ManagerUiState> = _uiState.asStateFlow()

    fun loadPendingRequests() {
        // Pending requests list
        viewModelScope.launch {
            leaveRepository.getPendingLeaveRequests().collect { requests ->
                _uiState.update { it.copy(pendingRequests = requests) }
            }
        }
        // Approved count
        viewModelScope.launch {
            leaveRepository.getApprovedCount().collect { count ->
                _uiState.update { it.copy(approvedCount = count) }
            }
        }
        // Rejected count
        viewModelScope.launch {
            leaveRepository.getRejectedCount().collect { count ->
                _uiState.update { it.copy(rejectedCount = count) }
            }
        }
        // Total count
        viewModelScope.launch {
            leaveRepository.getTotalCount().collect { count ->
                _uiState.update { it.copy(totalCount = count) }
            }
        }
    }

    fun selectRequest(request: LeaveRequest) {
        _uiState.update { it.copy(selectedRequest = request) }
    }

    fun approveRequest(manager: User, requestId: String, comment: String) {
        updateStatus(manager, requestId, "APPROVED", comment)
    }

    fun rejectRequest(manager: User, requestId: String, comment: String) {
        if (comment.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please provide a reason for rejection.") }
            return
        }
        updateStatus(manager, requestId, "REJECTED", comment)
    }

    private fun updateStatus(manager: User, requestId: String, status: String, comment: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "", successMessage = "") }
            val result = leaveRepository.updateLeaveStatus(
                requestId = requestId,
                status    = status,
                managerId = manager.id,
                comment   = comment.ifBlank { null }
            )
            when (result) {
                is Result.Success -> _uiState.update {
                    it.copy(
                        isLoading      = false,
                        successMessage = "Request ${status.lowercase()}d successfully.",
                        selectedRequest = null
                    )
                }
                is Result.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                else -> {}
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = "", successMessage = "") }
    }
}
