package com.leaveflow.app.ui.manager

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leaveflow.app.domain.model.LeaveRequest
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveDetailScreen(
    request: LeaveRequest,
    manager: User,
    viewModel: ManagerViewModel,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var comment by remember { mutableStateOf("") }
    var showConfirmApprove by remember { mutableStateOf(false) }
    var showConfirmReject  by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.value.successMessage) {
        if (uiState.value.successMessage.isNotBlank()) {
            kotlinx.coroutines.delay(1500)
            viewModel.clearMessages()
            onBack()
        }
    }

    // Approve confirmation dialog
    if (showConfirmApprove) {
        AlertDialog(
            onDismissRequest = { showConfirmApprove = false },
            title            = { Text("Confirm Approval") },
            text             = { Text("Approve ${request.numberOfDays} day(s) of ${request.leaveType.lowercase()} leave for ${request.employeeName}?") },
            confirmButton    = {
                Button(
                    onClick = {
                        showConfirmApprove = false
                        viewModel.approveRequest(manager, request.id, comment)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusApproved, contentColor = Color.White)
                ) { Text("Approve") }
            },
            dismissButton    = { TextButton(onClick = { showConfirmApprove = false }) { Text("Cancel") } },
            containerColor   = SurfaceCard
        )
    }

    // Reject confirmation dialog
    if (showConfirmReject) {
        AlertDialog(
            onDismissRequest = { showConfirmReject = false },
            title            = { Text("Confirm Rejection") },
            text             = { Text("Reject this leave request? A comment is required.") },
            confirmButton    = {
                Button(
                    onClick = {
                        showConfirmReject = false
                        viewModel.rejectRequest(manager, request.id, comment)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusRejected, contentColor = Color.White)
                ) { Text("Reject") }
            },
            dismissButton    = { TextButton(onClick = { showConfirmReject = false }) { Text("Cancel") } },
            containerColor   = SurfaceCard
        )
    }

    Scaffold(
        containerColor = Navy900,
        topBar = {
            TopAppBar(
                title = { Text("Review Leave Request") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = TextPrimary) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Navy800)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Messages
            ErrorBanner(uiState.value.errorMessage)
            SuccessBanner(uiState.value.successMessage)

            // ── Employee Info ─────────────────────────────────────────────────
            DetailSection(title = "Employee") {
                InfoRow(Icons.Default.Person,   "Name",       request.employeeName)
                InfoRow(Icons.Default.Business, "Department", request.department)
            }

            // ── Leave Details ─────────────────────────────────────────────────
            DetailSection(title = "Leave Details") {
                InfoRow(LeaveTypeIcon(request.leaveType), "Type",       "${request.leaveType.lowercase().replaceFirstChar { it.uppercase() }} Leave")
                InfoRow(Icons.Default.CalendarToday,      "Start Date", DateUtil.formatForDisplay(request.startDate))
                InfoRow(Icons.Default.CalendarToday,      "End Date",   DateUtil.formatForDisplay(request.endDate))
                InfoRow(Icons.Default.Schedule,           "Duration",   "${request.numberOfDays} day(s)")
                InfoRow(Icons.Default.Notes,              "Reason",     request.reason)
                InfoRow(Icons.Default.Phone,              "Contact",    request.contactNumber)
            }

            // ── Attachments ───────────────────────────────────────────────────
            DetailSection(title = "Attachments") {
                if (request.photoPath != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Image, null, tint = Teal60, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Photo document attached ✓", style = MaterialTheme.typography.bodyMedium, color = StatusApproved)
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HideImage, null, tint = TextHint, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("No photo attached", style = MaterialTheme.typography.bodyMedium, color = TextHint)
                    }
                }

                if (request.latitude != null && request.longitude != null) {
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InfoRow(
                            Icons.Default.LocationOn, "GPS Location",
                            "Lat: ${"%.6f".format(request.latitude)}\nLng: ${"%.6f".format(request.longitude)}",
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedButton(
                            onClick = {
                                val uri    = Uri.parse("geo:${request.latitude},${request.longitude}?q=${request.latitude},${request.longitude}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            },
                            shape  = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentBlue)
                        ) { Text("Open Map") }
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOff, null, tint = TextHint, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("No GPS location attached", style = MaterialTheme.typography.bodyMedium, color = TextHint)
                    }
                }
            }

            // ── Manager Comment ───────────────────────────────────────────────
            DetailSection(title = "Manager Comment") {
                OutlinedTextField(
                    value         = comment,
                    onValueChange = { comment = it },
                    label         = { Text("Add comment (required for rejection)") },
                    minLines      = 3,
                    maxLines      = 5,
                    shape         = RoundedCornerShape(12.dp),
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
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // ── Action Buttons ────────────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick  = { showConfirmReject = true },
                    enabled  = !uiState.value.isLoading,
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = StatusRejected),
                    border   = androidx.compose.foundation.BorderStroke(1.5.dp, StatusRejected),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) {
                    Icon(Icons.Default.Cancel, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Reject", fontWeight = FontWeight.SemiBold)
                }
                Button(
                    onClick  = { showConfirmApprove = true },
                    enabled  = !uiState.value.isLoading,
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = StatusApproved, contentColor = Color.White),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) {
                    if (uiState.value.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Approve", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DetailSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(color = SurfaceCard, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = Teal60)
            SectionDivider()
            content()
        }
    }
}


