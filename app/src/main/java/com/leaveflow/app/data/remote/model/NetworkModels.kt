package com.leaveflow.app.data.remote.model

import com.google.gson.annotations.SerializedName

// ── Request Models ────────────────────────────────────────────────────────────

data class LoginRequest(
    val email: String,
    val password: String
)

data class LeaveRequestPayload(
    val id: String,
    @SerializedName("employee_id")   val employeeId: String,
    @SerializedName("employee_name") val employeeName: String,
    val department: String,
    @SerializedName("leave_type")    val leaveType: String,
    @SerializedName("start_date")    val startDate: String,
    @SerializedName("end_date")      val endDate: String,
    val reason: String,
    @SerializedName("contact_number") val contactNumber: String,
    @SerializedName("number_of_days") val numberOfDays: Int,
    val status: String = "PENDING",
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class StatusUpdateRequest(
    val status: String,
    @SerializedName("manager_id")      val managerId: String,
    @SerializedName("manager_comment") val managerComment: String?
)

// ── Response Models ───────────────────────────────────────────────────────────

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val token: String?,
    val user: RemoteUser?
)

data class RemoteUser(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val department: String,
    @SerializedName("employee_id") val employeeId: String
)

data class LeaveRequestResponse(
    val id: String,
    @SerializedName("employee_id")    val employeeId: String,
    @SerializedName("employee_name")  val employeeName: String,
    val department: String,
    @SerializedName("leave_type")     val leaveType: String,
    @SerializedName("start_date")     val startDate: String,
    @SerializedName("end_date")       val endDate: String,
    val reason: String,
    @SerializedName("contact_number") val contactNumber: String,
    @SerializedName("number_of_days") val numberOfDays: Int,
    val status: String,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("manager_id")      val managerId: String?,
    @SerializedName("manager_comment") val managerComment: String?,
    @SerializedName("created_at")      val createdAt: String?
)

data class LeaveBalanceResponse(
    @SerializedName("employee_id")   val employeeId: String,
    @SerializedName("annual_total")  val annualTotal: Int,
    @SerializedName("annual_used")   val annualUsed: Int,
    @SerializedName("casual_total")  val casualTotal: Int,
    @SerializedName("casual_used")   val casualUsed: Int,
    @SerializedName("medical_total") val medicalTotal: Int,
    @SerializedName("medical_used")  val medicalUsed: Int,
    @SerializedName("nopay_used")    val noPayUsed: Int
)

data class LeaveSummaryResponse(
    val total: Int,
    val approved: Int,
    val pending: Int,
    val rejected: Int
)

data class MessageResponse(
    val success: Boolean,
    val message: String
)
