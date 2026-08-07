package com.leaveflow.app.data.remote

import com.leaveflow.app.data.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ── Authentication ────────────────────────────────────────────────────────
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // ── Leave Requests (Employee) ─────────────────────────────────────────────
    @POST("api/leaves")
    suspend fun submitLeaveRequest(@Body request: LeaveRequestPayload): Response<LeaveRequestResponse>

    @GET("api/leaves/employee/{employeeId}")
    suspend fun getLeavesByEmployee(
        @Path("employeeId") employeeId: String
    ): Response<List<LeaveRequestResponse>>

    @DELETE("api/leaves/{id}")
    suspend fun deleteLeaveRequest(@Path("id") id: String): Response<MessageResponse>

    // ── Leave Requests (Manager) ──────────────────────────────────────────────
    @GET("api/leaves/pending")
    suspend fun getPendingRequests(): Response<List<LeaveRequestResponse>>

    @PUT("api/leaves/{id}/status")
    suspend fun updateLeaveStatus(
        @Path("id") id: String,
        @Body update: StatusUpdateRequest
    ): Response<LeaveRequestResponse>

    // ── HR Summary ────────────────────────────────────────────────────────────
    @GET("api/leaves")
    suspend fun getAllLeaves(): Response<List<LeaveRequestResponse>>

    @GET("api/leaves/summary")
    suspend fun getLeaveSummary(): Response<LeaveSummaryResponse>

    // ── Balances ──────────────────────────────────────────────────────────────
    @GET("api/balances/{employeeId}")
    suspend fun getBalance(
        @Path("employeeId") employeeId: String
    ): Response<LeaveBalanceResponse>
}
