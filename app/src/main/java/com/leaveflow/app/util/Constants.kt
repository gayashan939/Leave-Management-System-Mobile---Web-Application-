package com.leaveflow.app.util

object Constants {
    // Leave Types
    const val LEAVE_ANNUAL  = "ANNUAL"
    const val LEAVE_CASUAL  = "CASUAL"
    const val LEAVE_MEDICAL = "MEDICAL"
    const val LEAVE_NOPAY   = "NOPAY"

    // Leave Status
    const val STATUS_PENDING  = "PENDING"
    const val STATUS_APPROVED = "APPROVED"
    const val STATUS_REJECTED = "REJECTED"

    // Sync Status
    const val SYNC_PENDING = "PENDING_SYNC"
    const val SYNC_SYNCED  = "SYNCED"
    const val SYNC_FAILED  = "FAILED"

    // User Roles
    const val ROLE_EMPLOYEE = "EMPLOYEE"
    const val ROLE_MANAGER  = "MANAGER"
    const val ROLE_HR       = "HR"

    // Session keys (DataStore)
    const val PREF_USER_ID   = "user_id"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_ROLE = "user_role"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_USER_DEPT = "user_department"
    const val PREF_EMP_ID    = "employee_id"
    const val PREF_LOGGED_IN = "is_logged_in"

    // Validation
    const val MIN_REASON_LENGTH = 10
    const val DATE_FORMAT       = "yyyy-MM-dd"
    const val DISPLAY_DATE_FORMAT = "dd MMM yyyy"
}
