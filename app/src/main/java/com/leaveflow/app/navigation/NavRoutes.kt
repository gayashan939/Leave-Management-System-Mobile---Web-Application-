package com.leaveflow.app.navigation

object NavRoutes {
    const val LOGIN              = "login"
    const val EMPLOYEE_DASHBOARD = "employee_dashboard"
    const val SUBMIT_LEAVE       = "submit_leave"
    const val LEAVE_HISTORY      = "leave_history"
    const val MANAGER_DASHBOARD  = "manager_dashboard"
    const val LEAVE_DETAIL       = "leave_detail/{requestId}"
    const val HR_DASHBOARD       = "hr_dashboard"
    const val CAMERA             = "camera"

    fun leaveDetail(requestId: String) = "leave_detail/$requestId"
}
