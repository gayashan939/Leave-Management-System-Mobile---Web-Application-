package com.leaveflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.leaveflow.app.navigation.NavGraph
import com.leaveflow.app.ui.auth.AuthViewModel
import com.leaveflow.app.ui.theme.LeaveFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeaveFlowTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                NavGraph(authViewModel = authViewModel)
            }
        }
    }
}
