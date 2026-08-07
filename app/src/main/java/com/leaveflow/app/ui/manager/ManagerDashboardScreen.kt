package com.leaveflow.app.ui.manager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerDashboardScreen(
    manager: User,
    viewModel: ManagerViewModel,
    onSelectRequest: (LeaveRequest) -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadPendingRequests() }

    Scaffold(
        containerColor = Navy900,
        topBar = {
            GradientTopBar(
                title    = "Manager Portal",
                subtitle = manager.name + " · " + manager.department
            ) {
                IconButton(onClick = onLogout) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout", tint = ErrorRed)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            // ── Messages ───────────────────────────────────────────────────────
            item {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    SuccessBanner(uiState.successMessage)
                    ErrorBanner(uiState.errorMessage)
                }
            }

            // ── Welcome Header Card ────────────────────────────────────────────
            item {
                WelcomeCard(manager = manager)
            }

            // ── Stats Row ─────────────────────────────────────────────────────
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Overview",
                    style    = MaterialTheme.typography.titleMedium,
                    color    = TextPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        label     = "Pending",
                        value     = uiState.pendingRequests.size,
                        icon      = Icons.Default.Pending,
                        color     = StatusPending,
                        bgColor   = StatusPendingBg,
                        modifier  = Modifier.weight(1f)
                    )
                    StatCard(
                        label     = "Approved",
                        value     = uiState.approvedCount,
                        icon      = Icons.Default.CheckCircle,
                        color     = StatusApproved,
                        bgColor   = StatusApprovedBg,
                        modifier  = Modifier.weight(1f)
                    )
                    StatCard(
                        label     = "Rejected",
                        value     = uiState.rejectedCount,
                        icon      = Icons.Default.Cancel,
                        color     = StatusRejected,
                        bgColor   = StatusRejectedBg,
                        modifier  = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(8.dp))
                // Total requests wide card
                Surface(
                    color    = SurfaceCard,
                    shape    = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier          = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Assessment, null, tint = AccentBlue, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(10.dp))
                            Text("Total Requests Managed", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        }
                        Text(
                            "${uiState.totalCount}",
                            style      = MaterialTheme.typography.headlineSmall,
                            color      = AccentBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // ── Section Header ─────────────────────────────────────────────────
            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(4.dp, 20.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Teal60)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Pending Approvals",
                            style      = MaterialTheme.typography.titleMedium,
                            color      = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (uiState.pendingRequests.isNotEmpty()) {
                        Surface(
                            color = StatusPendingBg,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                "${uiState.pendingRequests.size} awaiting",
                                style    = MaterialTheme.typography.labelSmall,
                                color    = StatusPending,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            // ── Pending Requests List / Empty State ────────────────────────────
            if (uiState.pendingRequests.isEmpty()) {
                item {
                    AllCaughtUpCard()
                }
            } else {
                itemsIndexed(
                    uiState.pendingRequests,
                    key = { _, req -> req.id }
                ) { index, request ->
                    AnimatedVisibility(
                        visible = true,
                        enter   = fadeIn(tween(300, delayMillis = index * 60)) +
                                  slideInVertically(tween(300, delayMillis = index * 60)) { it / 3 }
                    ) {
                        PendingRequestCard(
                            request  = request,
                            onReview = { onSelectRequest(request) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

// ── Welcome Header Card ────────────────────────────────────────────────────────

@Composable
private fun WelcomeCard(manager: User) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(TealDark, Color(0xFF0D2137))
                )
            )
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Welcome back,",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    manager.name,
                    style      = MaterialTheme.typography.headlineSmall,
                    color      = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Business, null, tint = Teal60, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(manager.department, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Default.ManageAccounts, null, tint = AccentBlue, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Manager", style = MaterialTheme.typography.bodySmall, color = AccentBlue)
                }
            }
            // Avatar
            Box(
                modifier         = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(Teal60, AccentBlue))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = manager.name.take(2).uppercase(),
                    color      = Navy900,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 20.sp
                )
            }
        }
    }
}

// ── Stat Card ──────────────────────────────────────────────────────────────────

@Composable
private fun StatCard(
    label:   String,
    value:   Int,
    icon:    ImageVector,
    color:   Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier            = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier         = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
            }
            Text(
                "$value",
                style      = MaterialTheme.typography.headlineMedium,
                color      = color,
                fontWeight = FontWeight.Bold
            )
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}

// ── All Caught Up Card ────────────────────────────────────────────────────────

@Composable
private fun AllCaughtUpCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceCard)
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier         = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(StatusApprovedBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    null,
                    tint     = StatusApproved,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                "All caught up!",
                style      = MaterialTheme.typography.titleLarge,
                color      = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                "No pending leave requests at the moment.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

// ── Pending Request Card ──────────────────────────────────────────────────────

@Composable
private fun PendingRequestCard(
    request:  LeaveRequest,
    onReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val leaveColor = when (request.leaveType.uppercase()) {
        "ANNUAL"  -> Color(0xFF00BCD4)
        "CASUAL"  -> Color(0xFFFFB300)
        "MEDICAL" -> Color(0xFFE91E63)
        else      -> TextSecondary
    }

    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(18.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            // Colored top accent bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(listOf(leaveColor, leaveColor.copy(alpha = 0.3f)))
                    )
            )
            Column(Modifier.padding(16.dp)) {
                // Employee row
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier         = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(leaveColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            request.employeeName.take(2).uppercase(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color      = leaveColor,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            request.employeeName,
                            style      = MaterialTheme.typography.titleMedium,
                            color      = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            maxLines   = 1,
                            overflow   = TextOverflow.Ellipsis
                        )
                        Text(
                            request.department,
                            style   = MaterialTheme.typography.bodySmall,
                            color   = TextSecondary
                        )
                    }
                    StatusChip(request.status)
                }

                Spacer(Modifier.height(14.dp))

                // Leave type + days badge row
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = leaveColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.BeachAccess, null, tint = leaveColor, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "${request.leaveType.lowercase().replaceFirstChar { it.uppercase() }} Leave",
                                style = MaterialTheme.typography.labelMedium,
                                color = leaveColor
                            )
                        }
                    }
                    Surface(
                        color = SurfaceCard2,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Today, null, tint = Teal60, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "${request.numberOfDays} day${if (request.numberOfDays > 1) "s" else ""}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Teal60
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Date range
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.DateRange, null, tint = TextHint, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${DateUtil.formatForDisplay(request.startDate)}  →  ${DateUtil.formatForDisplay(request.endDate)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                // Reason preview
                if (request.reason.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Notes, null, tint = TextHint, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(
                            request.reason,
                            style    = MaterialTheme.typography.bodySmall,
                            color    = TextHint,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Attachment indicators
                val hasAttachments = request.photoPath != null || request.latitude != null
                if (hasAttachments) {
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (request.photoPath != null) {
                            AttachmentBadge(Icons.Default.Image, "Photo Attached")
                        }
                        if (request.latitude != null) {
                            AttachmentBadge(Icons.Default.LocationOn, "GPS Attached")
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
                SectionDivider()
                Spacer(Modifier.height(10.dp))

                // Review button
                Button(
                    onClick  = onReview,
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = Teal60,
                        contentColor   = Navy900
                    ),
                    modifier = Modifier.fillMaxWidth().height(46.dp)
                ) {
                    Icon(Icons.Default.RateReview, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Review Request", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AttachmentBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(SurfaceCard2)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Teal60, modifier = Modifier.size(12.dp))
        Spacer(Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = Teal60)
    }
}
