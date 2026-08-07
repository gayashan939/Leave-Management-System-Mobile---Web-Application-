package com.leaveflow.app.domain.model

/**
 * Domain model for a signed-in user – decoupled from Room entity and Remote DTO.
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val department: String,
    val employeeId: String,
    val managerId: String? = null
)

/**
 * Domain model for a leave request.
 */
data class LeaveRequest(
    val id: String,
    val employeeId: String,
    val employeeName: String,
    val department: String,
    val leaveType: String,
    val startDate: String,
    val endDate: String,
    val reason: String,
    val contactNumber: String,
    val numberOfDays: Int,
    val status: String,
    val photoPath: String?,
    val latitude: Double?,
    val longitude: Double?,
    val syncStatus: String,
    val managerId: String?,
    val managerComment: String?,
    val createdAt: Long
)

/**
 * Domain model for leave balance.
 */
data class LeaveBalance(
    val employeeId: String,
    val annualTotal: Int,
    val annualUsed: Int,
    val annualPending: Int,
    val casualTotal: Int,
    val casualUsed: Int,
    val casualPending: Int,
    val medicalTotal: Int,
    val medicalUsed: Int,
    val medicalPending: Int,
    val noPayUsed: Int,
    val noPayPending: Int
) {
    val annualRemaining: Int  get() = annualTotal  - annualUsed  - annualPending
    val casualRemaining: Int  get() = casualTotal  - casualUsed  - casualPending
    val medicalRemaining: Int get() = medicalTotal - medicalUsed - medicalPending
}

/**
 * HR summary stats.
 */
data class LeaveSummary(
    val total: Int,
    val approved: Int,
    val pending: Int,
    val rejected: Int
)

/** Generic result wrapper for repository operations. */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
