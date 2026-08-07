package com.leaveflow.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user stored locally in Room.
 * Roles: EMPLOYEE | MANAGER | HR
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val passwordHash: String,
    val role: String,         // "EMPLOYEE" | "MANAGER" | "HR"
    val department: String,
    val employeeId: String,
    val managerId: String? = null,  // For employees – their manager's id
    val createdAt: Long = System.currentTimeMillis()
)
