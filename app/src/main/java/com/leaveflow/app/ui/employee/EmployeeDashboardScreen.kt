package com.leaveflow.app.ui.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.DateUtil

@Composable
fun EmployeeDashboardScreen(
    user: User,
    viewModel: EmployeeViewModel,
    onSubmitLeave: () -> Unit,
    onViewHistory: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(user.id) { viewModel.loadForEmployee(user) }

    Scaffold(
        containerColor = Navy900,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick          = onSubmitLeave,
                icon             = { Icon(Icons.Default.Add, null) },
                text             = { Text("New Leave", fontWeight = FontWeight.SemiBold) },
                containerColor   = Teal60,
                contentColor     = Navy900
            )
        }
    ) { padding ->
        LazyColumn(
            modifier            = Modifier.fillMaxSize().padding(padding),
            contentPadding      = PaddingValues(bottom = 100.dp)
        ) {
            // ── Header ────────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(TealDark, Navy800)))
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Good day,", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                                Text(user.name, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Business, null, tint = Teal60, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(user.department, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                                }
                            }
                            // Avatar
                            Box(
                                modifier         = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(listOf(Teal60, AccentBlue))),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text       = user.name.take(2).uppercase(),
                                    color      = Navy900,
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 18.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Sync status strip
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (uiState.pendingSyncCount > 0) {
                                Icon(Icons.Default.CloudQueue, null, tint = StatusPending, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("${uiState.pendingSyncCount} item(s) pending sync", style = MaterialTheme.typography.labelSmall, color = StatusPending)
                                Spacer(Modifier.width(12.dp))
                                TextButton(
                                    onClick      = { viewModel.triggerSync() },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("Sync Now", style = MaterialTheme.typography.labelSmall, color = Teal60)
                                }
                            } else {
                                Icon(Icons.Default.CloudDone, null, tint = StatusApproved, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("All records synced", style = MaterialTheme.typography.labelSmall, color = StatusApproved)
                            }
                        }
                    }
                }
            }

            // ── Messages ──────────────────────────────────────────────────────
            item {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    ErrorBanner(uiState.errorMessage)
                    SuccessBanner(uiState.successMessage)
                }
            }

            // ── Leave Balances ────────────────────────────────────────────────
            item {
                Text(
                    "Leave Balances",
                    style  = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
            }

            item {
                val bal = uiState.balance
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        BalanceCard(
                            label     = "Annual Leave",
                            remaining = bal?.annualRemaining  ?: 0,
                            used      = bal?.annualUsed       ?: 0,
                            pending   = bal?.annualPending    ?: 0,
                            total     = bal?.annualTotal      ?: 20,
                            color     = AccentBlue,
                            modifier  = Modifier.weight(1f)
                        )
                        BalanceCard(
                            label     = "Casual Leave",
                            remaining = bal?.casualRemaining  ?: 0,
                            used      = bal?.casualUsed       ?: 0,
                            pending   = bal?.casualPending    ?: 0,
                            total     = bal?.casualTotal      ?: 10,
                            color     = Teal60,
                            modifier  = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        BalanceCard(
                            label     = "Medical Leave",
                            remaining = bal?.medicalRemaining ?: 0,
                            used      = bal?.medicalUsed      ?: 0,
                            pending   = bal?.medicalPending   ?: 0,
                            total     = bal?.medicalTotal     ?: 14,
                            color     = AccentPurple,
                            modifier  = Modifier.weight(1f)
                        )
                        Surface(
                            color    = SurfaceCard,
                            shape    = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("No-Pay Leave", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                                Spacer(Modifier.height(8.dp))
                                Text("${bal?.noPayUsed ?: 0}", style = MaterialTheme.typography.displayLarge.copy(fontSize = 36.sp, color = StatusPending))
                                Text("days used", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }

            // ── Quick Actions ─────────────────────────────────────────────────
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        icon    = Icons.Default.History,
                        label   = "View History",
                        onClick = onViewHistory,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon    = Icons.Default.Logout,
                        label   = "Logout",
                        onClick = onLogout,
                        modifier = Modifier.weight(1f),
                        tint    = ErrorRed
                    )
                }
            }

            // ── Recent Requests ───────────────────────────────────────────────
            item {
                Text(
                    "Recent Requests",
                    style    = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )
            }

            val recent = uiState.leaveRequests.take(5)
            if (recent.isEmpty()) {
                item {
                    EmptyState(
                        "No leave requests yet.\nTap the + button to submit one.",
                        Icons.Default.EventBusy
                    )
                }
            } else {
                items(recent) { request ->
                    LeaveRequestMiniCard(request = request, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun LeaveRequestMiniCard(request: LeaveRequest, modifier: Modifier = Modifier) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TealDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(LeaveTypeIcon(request.leaveType), null, tint = Teal60, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text  = "${request.leaveType.lowercase().replaceFirstChar { it.uppercase() }} Leave",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text  = "${DateUtil.formatForDisplay(request.startDate)} – ${DateUtil.formatForDisplay(request.endDate)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text  = "${request.numberOfDays} day(s)",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                StatusChip(request.status)
                Spacer(Modifier.height(4.dp))
                SyncStatusChip(request.syncStatus)
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: androidx.compose.ui.graphics.Color = Teal60
) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(12.dp),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(6.dp))
            Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
    }
}
