package com.leaveflow.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LeaveFlowDarkColorScheme = darkColorScheme(
    primary           = Teal60,
    onPrimary         = Navy900,
    primaryContainer  = TealDark,
    onPrimaryContainer = Teal80,
    secondary         = AccentBlue,
    onSecondary       = Navy900,
    background        = Navy900,
    onBackground      = TextPrimary,
    surface           = SurfaceCard,
    onSurface         = TextPrimary,
    surfaceVariant    = SurfaceCard2,
    onSurfaceVariant  = TextSecondary,
    outline           = DividerColor,
    error             = ErrorRed,
    onError           = Navy900
)

@Composable
fun LeaveFlowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LeaveFlowDarkColorScheme,
        typography  = Typography,
        content     = content
    )
}
