package com.leaveflow.app.ui.hr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
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
import com.leaveflow.app.domain.model.BlockedDateRange
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

    // Block-dates form state — stored as epoch millis, converted to string on submit
    var showBlockForm    by remember { mutableStateOf(false) }
    var blockStartMillis by remember { mutableStateOf<Long?>(null) }
    var blockEndMillis   by remember { mutableStateOf<Long?>(null) }
    var blockReason      by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { viewModel.load() }

    // Combined filter: status + search query (case-insensitive employee name match)
    val filtered = remember(uiState.allRequests, filterStatus, uiState.searchQuery) {
        uiState.allRequests
            .let { list ->
                if (filterStatus == "ALL") list else list.filter { it.status == filterStatus }
            }
            .let { list ->
                val q = uiState.searchQuery.trim()
                if (q.isBlank()) list
                else list.filter { it.employeeName.contains(q, ignoreCase = true) }
            }
    }

    // Auto-clear block messages after showing them
    LaunchedEffect(uiState.blockMessage) {
        if (uiState.blockMessage.isNotBlank()) {
            kotlinx.coroutines.delay(2500)
            viewModel.clearBlockMessages()
        }
    }
    LaunchedEffect(uiState.blockError) {
        if (uiState.blockError.isNotBlank()) {
            kotlinx.coroutines.delay(3000)
            viewModel.clearBlockMessages()
        }
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
            contentPadding = PaddingValues(bottom = 32.dp)
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
                Spacer(Modifier.height(8.dp))
            }

            // ── Employee Search Bar ────────────────────────────────────────────
            item {
                OutlinedTextField(
                    value         = uiState.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder   = { Text("Search by employee name…", color = TextHint) },
                    leadingIcon   = {
                        Icon(Icons.Default.Search, null, tint = Teal60, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon  = {
                        if (uiState.searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Clear, null, tint = TextHint, modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    singleLine    = true,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = Teal60,
                        unfocusedBorderColor    = DividerColor,
                        focusedLabelColor       = Teal60,
                        unfocusedLabelColor     = TextHint,
                        cursorColor             = Teal60,
                        focusedTextColor        = TextPrimary,
                        unfocusedTextColor      = TextPrimary,
                        focusedContainerColor   = SurfaceCard2,
                        unfocusedContainerColor = SurfaceCard
                    ),
                    shape    = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
            }

            // ── All Leave Requests header ──────────────────────────────────────
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        "All Leave Requests (${filtered.size})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (uiState.searchQuery.isNotBlank()) {
                        Text(
                            "Filtered by \"${uiState.searchQuery}\"",
                            style = MaterialTheme.typography.labelSmall,
                            color = Teal60
                        )
                    }
                }
            }

            if (filtered.isEmpty()) {
                item {
                    EmptyState(
                        if (uiState.searchQuery.isNotBlank())
                            "No requests found for \"${uiState.searchQuery}\"."
                        else
                            "No ${filterStatus.lowercase()} requests found.",
                        Icons.Default.Inbox
                    )
                }
            } else {
                items(filtered, key = { it.id }) { request ->
                    HRLeaveCard(
                        request  = request,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            // ── Blocked Date Periods Section ───────────────────────────────────
            item { Spacer(Modifier.height(8.dp)) }

            item {
                BlockedDatesSectionHeader(
                    count         = uiState.blockedDates.size,
                    isFormVisible = showBlockForm,
                    onToggleForm  = { showBlockForm = !showBlockForm }
                )
            }

            // Feedback banners for block operations
            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    SuccessBanner(uiState.blockMessage)
                    ErrorBanner(uiState.blockError)
                }
            }

            // Add-block form (collapsible)
            item {
                AnimatedVisibility(
                    visible = showBlockForm,
                    enter   = slideInVertically() + fadeIn(),
                    exit    = slideOutVertically() + fadeOut()
                ) {
                    BlockDateForm(
                        startMillis = blockStartMillis,
                        endMillis   = blockEndMillis,
                        reason      = blockReason,
                        onStartMillisChange = { blockStartMillis = it },
                        onEndMillisChange   = { blockEndMillis   = it },
                        onReasonChange      = { blockReason = it },
                        onSubmit = {
                            val fmt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                            viewModel.addBlockedDate(
                                startDate = fmt.format(java.util.Date(blockStartMillis!!)),
                                endDate   = fmt.format(java.util.Date(blockEndMillis!!)),
                                reason    = blockReason,
                                createdBy = hrUser.name
                            )
                            blockStartMillis = null
                            blockEndMillis   = null
                            blockReason      = ""
                            showBlockForm    = false
                        }
                    )
                }
            }

            if (uiState.blockedDates.isEmpty()) {
                item {
                    Box(
                        modifier         = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceCard),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No date ranges blocked yet.",
                            style    = MaterialTheme.typography.bodySmall,
                            color    = TextHint,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            } else {
                items(uiState.blockedDates, key = { "block-${it.id}" }) { block ->
                    BlockedDateCard(
                        block    = block,
                        onDelete = { viewModel.removeBlockedDate(block.id) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// ── Summary Stat Card ─────────────────────────────────────────────────────────

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

// ── HR Leave Card ─────────────────────────────────────────────────────────────

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
                    Text(
                        request.employeeName,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
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

// ── Blocked Dates Section Header ──────────────────────────────────────────────

@Composable
private fun BlockedDatesSectionHeader(
    count: Int,
    isFormVisible: Boolean,
    onToggleForm: () -> Unit
) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier         = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ErrorRed.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Block,
                    null,
                    tint     = ErrorRed,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    "Blocked Date Periods",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "$count active block(s)",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (count > 0) ErrorRed else TextHint
                )
            }
        }
        Button(
            onClick  = onToggleForm,
            shape    = RoundedCornerShape(10.dp),
            colors   = ButtonDefaults.buttonColors(
                containerColor = if (isFormVisible) SurfaceCard2 else ErrorRed.copy(alpha = 0.85f),
                contentColor   = if (isFormVisible) TextSecondary else Color.White
            ),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Icon(
                if (isFormVisible) Icons.Default.ExpandLess else Icons.Default.Add,
                null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                if (isFormVisible) "Cancel" else "Block Dates",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
    HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 16.dp))
    Spacer(Modifier.height(4.dp))
}

// ── Block Date Form ───────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlockDateForm(
    startMillis: Long?,
    endMillis: Long?,
    reason: String,
    onStartMillisChange: (Long?) -> Unit,
    onEndMillisChange: (Long?) -> Unit,
    onReasonChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    // Track which picker is open
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker   by remember { mutableStateOf(false) }

    val displayFmt = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
    val startLabel = if (startMillis != null) displayFmt.format(java.util.Date(startMillis)) else "Select date"
    val endLabel   = if (endMillis   != null) displayFmt.format(java.util.Date(endMillis))   else "Select date"

    // ── Start Date Picker Dialog ──────────────────────────────────────────────
    if (showStartPicker) {
        val state = rememberDatePickerState(initialSelectedDateMillis = startMillis)
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton    = {
                TextButton(onClick = {
                    onStartMillisChange(state.selectedDateMillis)
                    showStartPicker = false
                }) { Text("OK", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(onClick = { showStartPicker = false }) { Text("Cancel") }
            },
            colors = DatePickerDefaults.colors(
                containerColor              = SurfaceCard,
                headlineContentColor        = ErrorRed,
                selectedDayContainerColor   = ErrorRed,
                todayDateBorderColor        = ErrorRed
            )
        ) {
            DatePicker(state = state)
        }
    }

    // ── End Date Picker Dialog ────────────────────────────────────────────────
    if (showEndPicker) {
        val state = rememberDatePickerState(initialSelectedDateMillis = endMillis)
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton    = {
                TextButton(onClick = {
                    onEndMillisChange(state.selectedDateMillis)
                    showEndPicker = false
                }) { Text("OK", color = ErrorRed) }
            },
            dismissButton = {
                TextButton(onClick = { showEndPicker = false }) { Text("Cancel") }
            },
            colors = DatePickerDefaults.colors(
                containerColor              = SurfaceCard,
                headlineContentColor        = ErrorRed,
                selectedDayContainerColor   = ErrorRed,
                todayDateBorderColor        = ErrorRed
            )
        ) {
            DatePicker(state = state)
        }
    }
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header row with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier         = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ErrorRed.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.EventBusy, null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "New Blocked Period",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = ErrorRed
                )
            }

            // Date picker buttons row
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DatePickerButton(
                    label     = "Start Date",
                    selected  = startLabel,
                    hasValue  = startMillis != null,
                    onClick   = { showStartPicker = true },
                    modifier  = Modifier.weight(1f)
                )
                DatePickerButton(
                    label     = "End Date",
                    selected  = endLabel,
                    hasValue  = endMillis != null,
                    onClick   = { showEndPicker = true },
                    modifier  = Modifier.weight(1f)
                )
            }

            // Reason field
            OutlinedTextField(
                value         = reason,
                onValueChange = onReasonChange,
                label         = { Text("Reason / Note") },
                leadingIcon   = { Icon(Icons.Default.Notes, null, tint = ErrorRed) },
                singleLine    = true,
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = ErrorRed,
                    unfocusedBorderColor    = DividerColor,
                    focusedLabelColor       = ErrorRed,
                    unfocusedLabelColor     = TextHint,
                    cursorColor             = ErrorRed,
                    focusedTextColor        = TextPrimary,
                    unfocusedTextColor      = TextPrimary,
                    focusedContainerColor   = SurfaceCard2,
                    unfocusedContainerColor = SurfaceCard
                ),
                shape    = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick  = onSubmit,
                enabled  = startMillis != null && endMillis != null,
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = ErrorRed,
                    contentColor           = Color.White,
                    disabledContainerColor = ErrorRed.copy(alpha = 0.38f),
                    disabledContentColor   = Color.White.copy(alpha = 0.6f)
                ),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.Block, null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Block This Period", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ── Date Picker Button (replaces text field for block dates) ──────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerButton(
    label: String,
    selected: String,
    hasValue: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (hasValue) ErrorRed else DividerColor
    val textColor   = if (hasValue) TextPrimary else TextHint

    Column(modifier = modifier) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = if (hasValue) ErrorRed else TextHint,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        Surface(
            color    = SurfaceCard2,
            shape    = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
        ) {
            Row(
                modifier          = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CalendarMonth,
                    null,
                    tint     = ErrorRed,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    selected,
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = textColor,
                    fontWeight = if (hasValue) FontWeight.Medium else FontWeight.Normal
                )
            }
        }
    }
}

// ── Blocked Date Card ─────────────────────────────────────────────────────────

@Composable
private fun BlockedDateCard(
    block: BlockedDateRange,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirm by remember { mutableStateOf(false) }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title            = { Text("Remove Block") },
            text             = {
                Text("Remove the blocked period from ${DateUtil.formatForDisplay(block.startDate)} to ${DateUtil.formatForDisplay(block.endDate)}? Employees will be able to apply for leave during this period again.")
            },
            confirmButton = {
                TextButton(onClick = { onDelete(); showConfirm = false }) {
                    Text("Remove", color = ErrorRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("Cancel") }
            },
            containerColor = SurfaceCard
        )
    }

    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, ErrorRed.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier         = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ErrorRed.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Block, null, tint = ErrorRed, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "${DateUtil.formatForDisplay(block.startDate)} – ${DateUtil.formatForDisplay(block.endDate)}",
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary
                )
                if (block.reason.isNotBlank()) {
                    Text(
                        block.reason,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                Text(
                    "Blocked by ${block.createdBy}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )
            }
            IconButton(onClick = { showConfirm = true }) {
                Icon(Icons.Default.DeleteOutline, null, tint = ErrorRed)
            }
        }
    }
}
