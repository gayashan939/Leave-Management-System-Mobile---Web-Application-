package com.leaveflow.app.ui.employee

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.leaveflow.app.domain.model.User
import com.leaveflow.app.ui.common.*
import com.leaveflow.app.ui.theme.*
import com.leaveflow.app.util.Constants
import com.leaveflow.app.util.DateUtil
import com.leaveflow.app.util.LocationHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SubmitLeaveScreen(
    user: User,
    viewModel: EmployeeViewModel,
    onNavigateToCamera: (onPhotoTaken: (String) -> Unit) -> Unit,
    onBack: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope   = rememberCoroutineScope()

    // Location permission state (runtime request)
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    var gpsPermissionDenied by remember { mutableStateOf(false) }

    // Form state
    var leaveType     by remember { mutableStateOf(Constants.LEAVE_ANNUAL) }
    var startDate     by remember { mutableStateOf("") }
    var endDate       by remember { mutableStateOf("") }
    var reason        by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var photoPath     by remember { mutableStateOf<String?>(null) }
    var latitude      by remember { mutableStateOf<Double?>(null) }
    var longitude     by remember { mutableStateOf<Double?>(null) }
    var gpsLoading    by remember { mutableStateOf(false) }
    var typeMenuExpanded by remember { mutableStateOf(false) }

    val calculatedDays = remember(startDate, endDate) {
        if (startDate.isNotBlank() && endDate.isNotBlank() && DateUtil.isValidRange(startDate, endDate))
            DateUtil.calculateDays(startDate, endDate) else 0
    }

    // After permission dialog closes — auto-fetch if granted, show error if denied
    LaunchedEffect(locationPermissions.allPermissionsGranted, locationPermissions.shouldShowRationale) {
        if (locationPermissions.allPermissionsGranted && latitude == null && longitude == null && !gpsLoading) {
            // Permissions just granted; fetch location automatically only if user already tapped the button
            // (no auto-fetch on cold open — only trigger on button press above)
        } else if (!locationPermissions.allPermissionsGranted &&
                   !locationPermissions.shouldShowRationale &&
                   locationPermissions.permissions.any { !it.status.isGranted }
        ) {
            // Permission permanently denied (shouldShowRationale=false after a denial means permanent deny)
            gpsPermissionDenied = true
        }
    }

    // Success → navigate back
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage.isNotBlank()) {
            kotlinx.coroutines.delay(1500)
            viewModel.clearMessages()
            onSubmitSuccess()
        }
    }

    Scaffold(
        containerColor = Navy900,
        topBar = {
            TopAppBar(
                title = { Text("Submit Leave Request", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary)
                    }
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
            ErrorBanner(uiState.errorMessage)
            SuccessBanner(uiState.successMessage)

            // ── Leave Type Selector ───────────────────────────────────────────
            FormSection(title = "Leave Type") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        Constants.LEAVE_ANNUAL  to "Annual",
                        Constants.LEAVE_CASUAL  to "Casual",
                        Constants.LEAVE_MEDICAL to "Medical",
                        Constants.LEAVE_NOPAY   to "No-Pay"
                    ).forEach { (type, label) ->
                        FilterChip(
                            selected = leaveType == type,
                            onClick  = { leaveType = type },
                            label    = { Text(label, fontSize = 12.sp) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor  = Teal60,
                                selectedLabelColor      = Navy900,
                                containerColor          = SurfaceCard2,
                                labelColor              = TextSecondary
                            )
                        )
                    }
                }
            }

            // ── Dates ─────────────────────────────────────────────────────────
            FormSection(title = "Leave Period") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    DateField(
                        value     = startDate,
                        onValueChange = { startDate = it },
                        label     = "Start Date",
                        modifier  = Modifier.weight(1f)
                    )
                    DateField(
                        value     = endDate,
                        onValueChange = { endDate = it },
                        label     = "End Date",
                        modifier  = Modifier.weight(1f)
                    )
                }
                if (calculatedDays > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, null, tint = Teal60, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("$calculatedDays day(s) requested", style = MaterialTheme.typography.labelMedium, color = Teal60)
                    }
                }
            }

            // ── Reason ────────────────────────────────────────────────────────
            FormSection(title = "Reason for Leave") {
                OutlinedTextField(
                    value         = reason,
                    onValueChange = { reason = it },
                    label         = { Text("Describe your reason (min. 10 chars)") },
                    minLines      = 3,
                    maxLines      = 5,
                    colors        = formFieldColors(),
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.fillMaxWidth(),
                    trailingIcon  = {
                        Text(
                            "${reason.length}",
                            style    = MaterialTheme.typography.labelSmall,
                            color    = if (reason.length >= 10) Teal60 else TextHint,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                )
            }

            // ── Contact Number ────────────────────────────────────────────────
            FormSection(title = "Contact During Leave") {
                OutlinedTextField(
                    value           = contactNumber,
                    onValueChange   = { contactNumber = it },
                    label           = { Text("Phone Number") },
                    leadingIcon     = { Icon(Icons.Default.Phone, null, tint = Teal60) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                    singleLine      = true,
                    colors          = formFieldColors(),
                    shape           = RoundedCornerShape(12.dp),
                    modifier        = Modifier.fillMaxWidth()
                )
            }

            // ── Camera ────────────────────────────────────────────────────────
            FormSection(title = "Supporting Document (Optional)") {
                if (photoPath != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model             = photoPath,
                            contentDescription = "Captured document",
                            contentScale      = ContentScale.Crop,
                            modifier          = Modifier.fillMaxSize()
                        )
                        IconButton(
                            onClick  = { photoPath = null },
                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                        ) {
                            Icon(Icons.Default.Cancel, null, tint = ErrorRed)
                        }
                    }
                } else {
                    OutlinedButton(
                        onClick = { onNavigateToCamera { path -> photoPath = path } },
                        shape   = RoundedCornerShape(12.dp),
                        colors  = ButtonDefaults.outlinedButtonColors(contentColor = Teal60),
                        border  = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Capture Document / Medical Certificate")
                    }
                }
            }

            // ── GPS Location ──────────────────────────────────────────────────
            FormSection(title = "GPS Location (Optional)") {
                if (latitude != null && longitude != null) {
                    Row(
                        modifier          = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceCard2)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocationOn, null, tint = Teal60, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Location Captured", style = MaterialTheme.typography.labelMedium, color = Teal60)
                            Text(
                                "Lat: ${"%.6f".format(latitude)}, Lng: ${"%.6f".format(longitude)}",
                                style = MaterialTheme.typography.labelSmall, color = TextHint
                            )
                        }
                        IconButton(onClick = { latitude = null; longitude = null }) {
                            Icon(Icons.Default.Cancel, null, tint = ErrorRed)
                        }
                    }
                } else {
                    // Show permission-denied warning if applicable
                    if (gpsPermissionDenied) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(ErrorRed.copy(alpha = 0.12f))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.LocationOff, null, tint = ErrorRed, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Location permission denied. Please enable it in Settings.",
                                style = MaterialTheme.typography.labelSmall,
                                color = ErrorRed
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                    }
                    OutlinedButton(
                        onClick  = {
                            gpsPermissionDenied = false
                            if (locationPermissions.allPermissionsGranted) {
                                // Permission already granted — fetch location
                                scope.launch {
                                    gpsLoading = true
                                    val loc = LocationHelper.getCurrentLocation(context)
                                    if (loc != null) {
                                        latitude  = loc.latitude
                                        longitude = loc.longitude
                                    }
                                    gpsLoading = false
                                }
                            } else {
                                // Request permissions; result handled in LaunchedEffect below
                                locationPermissions.launchMultiplePermissionRequest()
                            }
                        },
                        enabled  = !gpsLoading,
                        shape    = RoundedCornerShape(12.dp),
                        colors   = ButtonDefaults.outlinedButtonColors(contentColor = Teal60),
                        border   = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    ) {
                        if (gpsLoading) {
                            CircularProgressIndicator(color = Teal60, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.GpsFixed, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Capture Current GPS Location")
                        }
                    }
                }
            }

            // ── Submit ────────────────────────────────────────────────────────
            Spacer(Modifier.height(4.dp))
            Button(
                onClick  = {
                    viewModel.submitLeave(
                        user          = user,
                        leaveType     = leaveType,
                        startDate     = startDate,
                        endDate       = endDate,
                        reason        = reason,
                        contactNumber = contactNumber,
                        photoPath     = photoPath,
                        latitude      = latitude,
                        longitude     = longitude
                    )
                },
                enabled  = !uiState.isLoading,
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = Teal60, contentColor = Navy900),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Navy900, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Default.Send, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Submit Leave Request", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

// ── Helper composables ────────────────────────────────────────────────────────

@Composable
private fun FormSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(color = SurfaceCard, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = Teal60)
            content()
        }
    }
}

@Composable
private fun DateField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value           = value,
        onValueChange   = onValueChange,
        label           = { Text(label) },
        placeholder     = { Text("yyyy-MM-dd", style = MaterialTheme.typography.labelSmall, color = TextHint) },
        leadingIcon     = { Icon(Icons.Default.CalendarMonth, null, tint = Teal60) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
        singleLine      = true,
        colors          = formFieldColors(),
        shape           = RoundedCornerShape(12.dp),
        modifier        = modifier
    )
}

@Composable
private fun formFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = Teal60,
    unfocusedBorderColor    = DividerColor,
    focusedLabelColor       = Teal60,
    unfocusedLabelColor     = TextHint,
    cursorColor             = Teal60,
    focusedTextColor        = TextPrimary,
    unfocusedTextColor      = TextPrimary,
    focusedContainerColor   = SurfaceCard2,
    unfocusedContainerColor = SurfaceCard
)
