package com.leaveflow.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leaveflow.app.ui.common.ErrorBanner
import com.leaveflow.app.ui.theme.*

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: (role: String) -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()

    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Navigate on successful login
    LaunchedEffect(uiState.isLoggedIn, uiState.currentUser) {
        if (uiState.isLoggedIn && uiState.currentUser != null) {
            onLoginSuccess(uiState.currentUser!!.role)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(60.dp))

            // ── App Logo / Header ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(Teal60, TealDark))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector       = Icons.Default.Work,
                    contentDescription = "LeaveFlow",
                    tint              = TextPrimary,
                    modifier          = Modifier.size(40.dp)
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text       = "LeaveFlow",
                style      = MaterialTheme.typography.displayMedium,
                color      = TextPrimary,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text  = "Leave Management System",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Spacer(Modifier.height(40.dp))

            // ── Login Card ────────────────────────────────────────────────────
            Surface(
                color  = SurfaceCard,
                shape  = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text   = "Welcome Back",
                        style  = MaterialTheme.typography.headlineSmall,
                        color  = TextPrimary
                    )
                    Text(
                        text  = "Sign in to your account",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(24.dp))

                    // Email field
                    OutlinedTextField(
                        value         = email,
                        onValueChange = { email = it; authViewModel.clearError() },
                        label         = { Text("Email Address") },
                        leadingIcon   = { Icon(Icons.Default.Email, null, tint = Teal60) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction    = ImeAction.Next
                        ),
                        singleLine   = true,
                        shape        = RoundedCornerShape(12.dp),
                        colors       = loginFieldColors(),
                        modifier     = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(14.dp))

                    // Password field
                    OutlinedTextField(
                        value         = password,
                        onValueChange = { password = it; authViewModel.clearError() },
                        label         = { Text("Password") },
                        leadingIcon   = { Icon(Icons.Default.Lock, null, tint = Teal60) },
                        trailingIcon  = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle password",
                                    tint = TextSecondary
                                )
                            }
                        },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction    = ImeAction.Done
                        ),
                        singleLine   = true,
                        shape        = RoundedCornerShape(12.dp),
                        colors       = loginFieldColors(),
                        modifier     = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))

                    // Error banner
                    ErrorBanner(uiState.errorMessage)
                    if (uiState.errorMessage.isNotBlank()) Spacer(Modifier.height(8.dp))

                    // Login button
                    Button(
                        onClick  = { authViewModel.login(email.trim(), password) },
                        enabled  = !uiState.isLoading && email.isNotBlank() && password.isNotBlank(),
                        shape    = RoundedCornerShape(12.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = Teal60, contentColor = Navy900),
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(color = Navy900, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Login, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Demo Credentials hint ─────────────────────────────────────────
            Surface(
                color    = SurfaceCard2,
                shape    = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Demo Accounts (all passwords: Pass@1234)", style = MaterialTheme.typography.labelSmall, color = TextHint, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    DemoAccountRow("Employee",   "john.doe@leaveflow.com")
                    DemoAccountRow("Manager",    "sarah.smith@leaveflow.com")
                    DemoAccountRow("HR Admin",   "admin.hr@leaveflow.com")
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun DemoAccountRow(role: String, email: String) {
    Row(
        modifier          = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(role,  style = MaterialTheme.typography.labelSmall, color = Teal60)
        Text(email, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = Teal60,
    unfocusedBorderColor = DividerColor,
    focusedLabelColor    = Teal60,
    unfocusedLabelColor  = TextHint,
    cursorColor          = Teal60,
    focusedTextColor     = TextPrimary,
    unfocusedTextColor   = TextPrimary,
    focusedContainerColor   = SurfaceCard2,
    unfocusedContainerColor = SurfaceCard
)
