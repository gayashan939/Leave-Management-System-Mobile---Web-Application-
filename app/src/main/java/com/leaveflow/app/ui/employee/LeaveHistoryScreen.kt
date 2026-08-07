package com.leaveflow.app.ui.employee

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.DateUtil
import com.leaveflow.app.util.LocationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveHistoryScreen(
    user: User,
    viewModel: EmployeeViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var filterStatus by remember { mutableStateOf("ALL") }
    var requestToDelete by remember { mutableStateOf<LeaveRequest?>(null) }

    // Each nav destination gets its own ViewModel; load data on entry
    LaunchedEffect(user.id) { viewModel.loadForEmployee(user) }
    val context = LocalContext.current

    val filtered = remember(uiState.leaveRequests, filterStatus) {
        if (filterStatus == "ALL") uiState.leaveRequests
        else uiState.leaveRequests.filter { it.status == filterStatus }
    }

    // Confirm delete dialog
    requestToDelete?.let { req ->
        AlertDialog(
            onDismissRequest = { requestToDelete = null },
            title            = { Text("Delete Request") },
            text             = { Text("Are you sure you want to delete this rejected leave request?") },
            confirmButton    = {
                TextButton(onClick = {
                    viewModel.deleteRejectedRequest(req.id)
                    requestToDelete = null
                }) { Text("Delete", color = ErrorRed) }
            },
            dismissButton    = { TextButton(onClick = { requestToDelete = null }) { Text("Cancel") } },
            containerColor   = SurfaceCard
        )
    }

    Scaffold(
        containerColor = Navy900,
        topBar = {
            TopAppBar(
                title = { Text("Leave History", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextPrimary) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Navy800)
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            // Filter chips
            Row(
                modifier             = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
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

            // Messages
            Column(Modifier.padding(horizontal = 16.dp)) {
                ErrorBanner(uiState.errorMessage)
                SuccessBanner(uiState.successMessage)
            }

            if (filtered.isEmpty()) {
                EmptyState("No ${filterStatus.lowercase()} requests found.", Icons.Default.EventBusy)
            } else {
                LazyColumn(
                    contentPadding        = PaddingValues(bottom = 24.dp),
                    verticalArrangement   = Arrangement.spacedBy(10.dp),
                    modifier              = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(filtered, key = { it.id }) { request ->
                        LeaveHistoryCard(
                            request         = request,
                            onDelete        = { requestToDelete = request },
                            onViewLocation  = { lat, lng ->
                                val uri    = Uri.parse("geo:$lat,$lng?q=$lat,$lng")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaveHistoryCard(
    request: LeaveRequest,
    onDelete: () -> Unit,
    onViewLocation: (Double, Double) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            // Header row
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier         = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(TealDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(LeaveTypeIcon(request.leaveType), null, tint = Teal60, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        "${request.leaveType.lowercase().replaceFirstChar { it.uppercase() }} Leave",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "${DateUtil.formatForDisplay(request.startDate)} – ${DateUtil.formatForDisplay(request.endDate)}  ·  ${request.numberOfDays} day(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                StatusChip(request.status)
            }

            Spacer(Modifier.height(10.dp))
            SectionDivider()

            // Sync + request ID row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                SyncStatusChip(request.syncStatus)
                Text(
                    "ID: ${request.id.take(8)}…",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint
                )
            }

            // Expandable details
            TextButton(
                onClick         = { expanded = !expanded },
                contentPadding  = PaddingValues(0.dp)
            ) {
                Text(
                    if (expanded) "Hide Details" else "View Details",
                    style = MaterialTheme.typography.labelMedium,
                    color = Teal60
                )
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    null, tint = Teal60, modifier = Modifier.size(18.dp)
                )
            }

            if (expanded) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoRow(Icons.Default.Notes,   "Reason",       request.reason)
                    InfoRow(Icons.Default.Phone,   "Contact",      request.contactNumber)
                    InfoRow(Icons.Default.Schedule,"Submitted",    DateUtil.formatTimestamp(request.createdAt))
                    if (request.managerComment != null) {
                        InfoRow(Icons.Default.Comment, "Manager Comment", request.managerComment)
                    }
                    if (request.latitude != null && request.longitude != null) {
                        Row(
                            modifier          = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InfoRow(
                                Icons.Default.LocationOn, "Location",
                                "Lat: ${"%.4f".format(request.latitude)}, Lng: ${"%.4f".format(request.longitude)}",
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = { onViewLocation(request.latitude, request.longitude) }) {
                                Text("Map", style = MaterialTheme.typography.labelMedium, color = AccentBlue)
                            }
                        }
                    }
                    if (request.photoPath != null) {
                        InfoRow(Icons.Default.Image, "Document", "Photo attached ✓")
                    }

                    // Delete button for rejected requests
                    if (request.status == "REJECTED") {
                        Spacer(Modifier.height(4.dp))
                        OutlinedButton(
                            onClick  = onDelete,
                            shape    = RoundedCornerShape(8.dp),
                            colors   = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                            border   = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Delete Request", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}
