package com.leaveflow.app.ui.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaveflow.app.data.repository.LeaveRepository
import com.leaveflow.app.data.repository.SyncRepository
import com.leaveflow.app.domain.model.LeaveBalance
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.Result
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.worker.SyncWorker
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmployeeUiState(
    val leaveRequests: List<LeaveRequest> = emptyList(),
    val balance: LeaveBalance?            = null,
    val pendingSyncCount: Int             = 0,
    val isLoading: Boolean                = false,
    val errorMessage: String              = "",
    val successMessage: String            = ""
)

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val leaveRepository: LeaveRepository,
    private val syncRepository: SyncRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState: StateFlow<EmployeeUiState> = _uiState.asStateFlow()

    fun loadForEmployee(user: User) {
        viewModelScope.launch {
            // Ensure balance record exists
            leaveRepository.ensureBalanceExists(user.id)

            // Observe leave requests
            leaveRepository.getLeavesByEmployee(user.id).collect { requests ->
                _uiState.update { it.copy(leaveRequests = requests) }
            }
        }
        viewModelScope.launch {
            leaveRepository.getBalanceByEmployee(user.id).collect { balance ->
                _uiState.update { it.copy(balance = balance) }
            }
        }
        viewModelScope.launch {
            syncRepository.pendingSyncCount.collect { count ->
                _uiState.update { it.copy(pendingSyncCount = count) }
            }
        }
    }

    fun submitLeave(
        user: User,
        leaveType: String,
        startDate: String,
        endDate: String,
        reason: String,
        contactNumber: String,
        photoPath: String?,
        latitude: Double?,
        longitude: Double?
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "", successMessage = "") }
            val result = leaveRepository.submitLeaveRequest(
                employeeId    = user.id,
                employeeName  = user.name,
                department    = user.department,
                leaveType     = leaveType,
                startDate     = startDate,
                endDate       = endDate,
                reason        = reason,
                contactNumber = contactNumber,
                photoPath     = photoPath,
                latitude      = latitude,
                longitude     = longitude
            )
            when (result) {
                is Result.Success -> _uiState.update {
                    it.copy(isLoading = false, successMessage = "Leave request submitted successfully!")
                }
                is Result.Error   -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                else -> {}
            }
        }
    }

    fun deleteRejectedRequest(requestId: String) {
        viewModelScope.launch {
            val result = leaveRepository.deleteRejectedRequest(requestId)
            if (result is Result.Error) {
                _uiState.update { it.copy(errorMessage = result.message) }
            } else {
                _uiState.update { it.copy(successMessage = "Request deleted.") }
            }
        }
    }

    fun triggerSync() {
        SyncWorker.triggerManualSync(context)
        _uiState.update { it.copy(successMessage = "Sync started…") }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = "", successMessage = "") }
    }
}
