package com.leaveflow.app.ui.hr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaveflow.app.data.repository.LeaveRepository
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.LeaveSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HRUiState(
    val allRequests: List<LeaveRequest> = emptyList(),
    val summary: LeaveSummary          = LeaveSummary(0, 0, 0, 0),
    val isLoading: Boolean             = false
)

@HiltViewModel
class HRViewModel @Inject constructor(
    private val leaveRepository: LeaveRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HRUiState())
    val uiState: StateFlow<HRUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            leaveRepository.getAllLeaveRequests().collect { requests ->
                _uiState.update { it.copy(allRequests = requests) }
            }
        }
        viewModelScope.launch {
            combine(
                leaveRepository.getTotalCount(),
                leaveRepository.getApprovedCount(),
                leaveRepository.getPendingCount(),
                leaveRepository.getRejectedCount()
            ) { total, approved, pending, rejected ->
                LeaveSummary(total, approved, pending, rejected)
            }.collect { summary ->
                _uiState.update { it.copy(summary = summary) }
            }
        }
    }
}
