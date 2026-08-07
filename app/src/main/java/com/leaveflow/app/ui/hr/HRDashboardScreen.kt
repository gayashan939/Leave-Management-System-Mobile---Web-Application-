package com.leaveflow.app.ui.hr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HRDashboardScreen(
    hrUser: User,
    viewModel: HRViewModel,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var filterStatus by remember { mutableStateOf("ALL") }

    LaunchedEffect(Unit) { viewModel.load() }

    val filtered = remember(uiState.allRequests, filterStatus) {
        if (filterStatus == "ALL") uiState.allRequests
        else uiState.allRequests.filter { it.status == filterStatus }
    }

    Scaffold(
        containerColor = Navy900,
        topBar = {
            GradientTopBar(
                title    = "HR Administration",
                subtitle = hrUser.name
            ) {
                IconButton(onClick = onLogout) {
                    Icon(Icons.Default.Logout, null, tint = ErrorRed)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ── Summary Cards ─────────────────────────────────────────────────
            item {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Leave Summary", style = MaterialTheme.typography.titleLarge)
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SummaryStatCard(
                            value   = uiState.summary.total,
                            label   = "Total",
                            icon    = Icons.Default.ListAlt,
                            color   = AccentBlue,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryStatCard(
                            value   = uiState.summary.pending,
                            label   = "Pending",
                            icon    = Icons.Default.HourglassTop,
                            color   = StatusPending,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SummaryStatCard(
                            value   = uiState.summary.approved,
                            label   = "Approved",
                            icon    = Icons.Default.CheckCircle,
                            color   = StatusApproved,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryStatCard(
                            value   = uiState.summary.rejected,
                            label   = "Rejected",
                            icon    = Icons.Default.Cancel,
                            color   = StatusRejected,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // ── Filter Chips ──────────────────────────────────────────────────
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("ALL", "PENDING", "APPROVED", "REJECTED").forEach { s ->
                        FilterChip(
                            selected = filterStatus == s,
                            onClick  = { filterStatus = s },
                            label    = { Text(s.lowercase().replaceFirstChar { it.uppercase() }) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Teal60,
                                selectedLabelColor     = Navy900,
                                containerColor         = SurfaceCard2,
                                labelColor             = TextSecondary
                            )
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
            }

            // ── All Leave Requests ────────────────────────────────────────────
            item {
                Text(
                    "All Leave Requests (${filtered.size})",
                    style    = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (filtered.isEmpty()) {
                item { EmptyState("No ${filterStatus.lowercase()} requests found.", Icons.Default.Inbox) }
            } else {
                items(filtered, key = { it.id }) { request ->
                    HRLeaveCard(request = request, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
private fun SummaryStatCard(
    value: Int,
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text       = "$value",
                fontSize   = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = color
            )
            Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
    }
}

@Composable
private fun HRLeaveCard(request: LeaveRequest, modifier: Modifier = Modifier) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TealDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(LeaveTypeIcon(request.leaveType), null, tint = Teal60, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(request.employeeName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    StatusChip(request.status)
                }
                Text(
                    "${request.department}  ·  ${request.leaveType.lowercase().replaceFirstChar { it.uppercase() }} Leave",
                    style = MaterialTheme.typography.bodySmall, color = TextSecondary
                )
                Text(
                    "${DateUtil.formatForDisplay(request.startDate)} – ${DateUtil.formatForDisplay(request.endDate)}  ·  ${request.numberOfDays} day(s)",
                    style = MaterialTheme.typography.labelSmall, color = TextHint
                )
            }
        }
    }
}
