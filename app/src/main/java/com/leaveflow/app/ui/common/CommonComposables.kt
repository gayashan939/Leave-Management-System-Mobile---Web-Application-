package com.leaveflow.app.ui.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leaveflow.app.ui.theme.*

// ── Status Chip ───────────────────────────────────────────────────────────────

@Composable
fun StatusChip(status: String) {
    val (bg, fg, label) = when (status.uppercase()) {
        "APPROVED" -> Triple(StatusApprovedBg, StatusApproved, "Approved")
        "REJECTED" -> Triple(StatusRejectedBg, StatusRejected, "Rejected")
        else       -> Triple(StatusPendingBg,  StatusPending,  "Pending")
    }
    Surface(
        color  = bg,
        shape  = RoundedCornerShape(50),
        modifier = Modifier.border(1.dp, fg.copy(0.4f), RoundedCornerShape(50))
    ) {
        Text(
            text     = label,
            color    = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

// ── Sync Status Chip ──────────────────────────────────────────────────────────

@Composable
fun SyncStatusChip(syncStatus: String) {
    val (bg, fg, label, icon) = when (syncStatus.uppercase()) {
        "SYNCED"       -> Quadruple(Color(0xFF003319), Color(0xFF00C853), "Synced",      Icons.Default.CloudDone)
        "FAILED"       -> Quadruple(Color(0xFF2D0A0A), Color(0xFFD32F2F), "Sync Failed", Icons.Default.CloudOff)
        else           -> Quadruple(Color(0xFF1A1500), Color(0xFFFFB300), "Pending Sync",Icons.Default.CloudQueue)
    }
    Surface(
        color    = bg,
        shape    = RoundedCornerShape(50),
        modifier = Modifier.border(1.dp, fg.copy(0.3f), RoundedCornerShape(50))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(12.dp))
            Spacer(Modifier.width(4.dp))
            Text(label, color = fg, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

private data class Quadruple<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

// ── Info Card Row ─────────────────────────────────────────────────────────────

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier          = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector       = icon,
            contentDescription = null,
            tint              = Teal60,
            modifier          = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextHint)
            Text(value, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
        }
    }
}

// ── Section Divider ───────────────────────────────────────────────────────────

@Composable
fun SectionDivider() {
    HorizontalDivider(
        modifier  = Modifier.padding(vertical = 8.dp),
        color     = DividerColor,
        thickness = 1.dp
    )
}

// ── Loading Screen ────────────────────────────────────────────────────────────

@Composable
fun FullScreenLoading() {
    Box(
        modifier          = Modifier
            .fillMaxSize()
            .background(Navy900),
        contentAlignment  = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Teal60)
            Spacer(Modifier.height(16.dp))
            Text("Loading...", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// ── Empty State ───────────────────────────────────────────────────────────────

@Composable
fun EmptyState(message: String, icon: ImageVector = Icons.Default.Inbox) {
    Box(
        modifier         = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector       = icon,
                contentDescription = null,
                tint              = TextHint,
                modifier          = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text      = message,
                color     = TextSecondary,
                style     = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── Error Banner ──────────────────────────────────────────────────────────────

@Composable
fun ErrorBanner(message: String) {
    AnimatedVisibility(
        visible = message.isNotBlank(),
        enter   = slideInVertically() + fadeIn(),
        exit    = slideOutVertically() + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2D0A0A))
                .border(1.dp, ErrorRed.copy(0.4f), RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Error, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(message, color = ErrorRed, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ── Success Banner ────────────────────────────────────────────────────────────

@Composable
fun SuccessBanner(message: String) {
    AnimatedVisibility(
        visible = message.isNotBlank(),
        enter   = slideInVertically() + fadeIn(),
        exit    = slideOutVertically() + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF003319))
                .border(1.dp, StatusApproved.copy(0.4f), RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusApproved, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(message, color = StatusApproved, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ── Gradient App Bar ──────────────────────────────────────────────────────────

@Composable
fun GradientTopBar(
    title: String,
    subtitle: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(listOf(TealDark, Navy700))
            )
            .statusBarsPadding()
            .padding(start = if (navigationIcon != null) 4.dp else 20.dp, end = 4.dp, top = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading navigation icon (back button etc.)
        if (navigationIcon != null) {
            navigationIcon()
            Spacer(Modifier.width(4.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            actions()
        }
    }
}

// ── Balance Card ──────────────────────────────────────────────────────────────

@Composable
fun BalanceCard(
    label: String,
    remaining: Int,
    used: Int,
    pending: Int,
    total: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color    = SurfaceCard,
        shape    = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Spacer(Modifier.height(8.dp))
            Text(
                text  = "$remaining",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 36.sp, color = color)
            )
            Text("days left", style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress       = { if (total > 0) (used + pending).toFloat() / total else 0f },
                modifier       = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color          = color,
                trackColor     = DividerColor
            )
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Used: $used", style = MaterialTheme.typography.labelSmall, color = TextHint)
                if (pending > 0)
                    Text("Pending: $pending", style = MaterialTheme.typography.labelSmall, color = StatusPending)
                Text("Total: $total", style = MaterialTheme.typography.labelSmall, color = TextHint)
            }
        }
    }
}

// ── Leave Request Card ────────────────────────────────────────────────────────

@Composable
fun LeaveTypeIcon(leaveType: String): ImageVector = when (leaveType.uppercase()) {
    "ANNUAL"  -> Icons.Default.BeachAccess
    "CASUAL"  -> Icons.Default.WbSunny
    "MEDICAL" -> Icons.Default.LocalHospital
    "NOPAY"   -> Icons.Default.MoneyOff
    else      -> Icons.Default.EventNote
}
