package com.leaveflow.app.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.ui.auth.AuthViewModel
import com.leaveflow.app.ui.auth.LoginScreen
import com.leaveflow.app.ui.camera.CameraScreen
import com.leaveflow.app.ui.employee.EmployeeDashboardScreen
import com.leaveflow.app.ui.employee.EmployeeViewModel
import com.leaveflow.app.ui.employee.LeaveHistoryScreen
import com.leaveflow.app.ui.employee.SubmitLeaveScreen
import com.leaveflow.app.ui.hr.HRDashboardScreen
import com.leaveflow.app.ui.hr.HRViewModel
import com.leaveflow.app.ui.manager.LeaveDetailScreen
import com.leaveflow.app.ui.manager.ManagerDashboardScreen
import com.leaveflow.app.ui.manager.ManagerViewModel
import com.leaveflow.app.util.Constants

@Composable
fun NavGraph(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val uiState by authViewModel.uiState.collectAsState()

    // Keep a reference to the selected leave request for manager detail
    var selectedLeaveRequest by remember { mutableStateOf<LeaveRequest?>(null) }
    // Callback for camera → submit leave
    var photoCallback by remember { mutableStateOf<((String) -> Unit)?>(null) }

    val startDestination = if (uiState.isLoggedIn && uiState.currentUser != null) {
        when (uiState.currentUser!!.role) {
            Constants.ROLE_MANAGER  -> NavRoutes.MANAGER_DASHBOARD
            Constants.ROLE_HR       -> NavRoutes.HR_DASHBOARD
            else                    -> NavRoutes.EMPLOYEE_DASHBOARD
        }
    } else NavRoutes.LOGIN

    NavHost(
        navController  = navController,
        startDestination = startDestination
    ) {
        // ── Login ─────────────────────────────────────────────────────────────
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                authViewModel  = authViewModel,
                onLoginSuccess = { role ->
                    val destination = when (role) {
                        Constants.ROLE_MANAGER -> NavRoutes.MANAGER_DASHBOARD
                        Constants.ROLE_HR      -> NavRoutes.HR_DASHBOARD
                        else                   -> NavRoutes.EMPLOYEE_DASHBOARD
                    }
                    navController.navigate(destination) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── Employee ──────────────────────────────────────────────────────────
        composable(NavRoutes.EMPLOYEE_DASHBOARD) {
            val employeeVm: EmployeeViewModel = hiltViewModel()
            val user = uiState.currentUser ?: return@composable
            EmployeeDashboardScreen(
                user         = user,
                viewModel    = employeeVm,
                onSubmitLeave = { navController.navigate(NavRoutes.SUBMIT_LEAVE) },
                onViewHistory = { navController.navigate(NavRoutes.LEAVE_HISTORY) },
                onLogout     = {
                    authViewModel.logout()
                    navController.navigate(NavRoutes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(NavRoutes.SUBMIT_LEAVE) {
            val employeeVm: EmployeeViewModel = hiltViewModel()
            val user = uiState.currentUser ?: return@composable
            SubmitLeaveScreen(
                user              = user,
                viewModel         = employeeVm,
                onNavigateToCamera = { callback ->
                    photoCallback = callback
                    navController.navigate(NavRoutes.CAMERA)
                },
                onBack            = { navController.popBackStack() },
                onSubmitSuccess   = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.LEAVE_HISTORY) {
            val employeeVm: EmployeeViewModel = hiltViewModel()
            val user = uiState.currentUser ?: return@composable
            LeaveHistoryScreen(
                user      = user,
                viewModel = employeeVm,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Camera ────────────────────────────────────────────────────────────
        composable(NavRoutes.CAMERA) {
            CameraScreen(
                onPhotoSaved = { path ->
                    photoCallback?.invoke(path)
                    photoCallback = null
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ── Manager ───────────────────────────────────────────────────────────
        composable(NavRoutes.MANAGER_DASHBOARD) {
            val managerVm: ManagerViewModel = hiltViewModel()
            val user = uiState.currentUser ?: return@composable
            ManagerDashboardScreen(
                manager         = user,
                viewModel       = managerVm,
                onSelectRequest = { request ->
                    selectedLeaveRequest = request
                    navController.navigate(NavRoutes.leaveDetail(request.id))
                },
                onLogout        = {
                    authViewModel.logout()
                    navController.navigate(NavRoutes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(
            route     = NavRoutes.LEAVE_DETAIL,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) {
            val managerVm: ManagerViewModel = hiltViewModel()
            val user    = uiState.currentUser ?: return@composable
            val request = selectedLeaveRequest ?: return@composable
            LeaveDetailScreen(
                request   = request,
                manager   = user,
                viewModel = managerVm,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── HR ────────────────────────────────────────────────────────────────
        composable(NavRoutes.HR_DASHBOARD) {
            val hrVm: HRViewModel = hiltViewModel()
            val user = uiState.currentUser ?: return@composable
            HRDashboardScreen(
                hrUser   = user,
                viewModel = hrVm,
                onLogout  = {
                    authViewModel.logout()
                    navController.navigate(NavRoutes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }
    }
}
