package com.leaveflow.app.ui.hr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaveflow.app.data.repository.BlockedDateRepository
import com.leaveflow.app.data.repository.LeaveRepository
import com.leaveflow.app.domain.model.BlockedDateRange
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.LeaveSummary
import com.leaveflow.app.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HRUiState(
    val allRequests: List<LeaveRequest>      = emptyList(),
    val summary: LeaveSummary                = LeaveSummary(0, 0, 0, 0),
    val isLoading: Boolean                   = false,
    // Search
    val searchQuery: String                  = "",
    // Blocked dates
    val blockedDates: List<BlockedDateRange> = emptyList(),
    val blockMessage: String                 = "",
    val blockError: String                   = ""
)

@HiltViewModel
class HRViewModel @Inject constructor(
    private val leaveRepository: LeaveRepository,
    private val blockedDateRepository: BlockedDateRepository
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
        viewModelScope.launch {
            blockedDateRepository.getAllBlockedDates().collect { dates ->
                _uiState.update { it.copy(blockedDates = dates) }
            }
        }
    }

    // ── Search ─────────────────────────────────────────────────────────────────

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // ── Blocked Dates ──────────────────────────────────────────────────────────

    fun addBlockedDate(startDate: String, endDate: String, reason: String, createdBy: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(blockMessage = "", blockError = "") }
            when (val result = blockedDateRepository.addBlockedDate(startDate, endDate, reason, createdBy)) {
                is Result.Success -> _uiState.update {
                    it.copy(blockMessage = "Date range blocked successfully.")
                }
                is Result.Error   -> _uiState.update {
                    it.copy(blockError = result.message)
                }
                else -> {}
            }
        }
    }

    fun removeBlockedDate(id: String) {
        viewModelScope.launch {
            blockedDateRepository.removeBlockedDate(id)
            _uiState.update { it.copy(blockMessage = "Blocked period removed.") }
        }
    }

    fun clearBlockMessages() {
        _uiState.update { it.copy(blockMessage = "", blockError = "") }
    }
}
