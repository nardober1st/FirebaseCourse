package com.bernardooechsler.firebasecourse.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    snackBar: SnackbarHostState,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBar)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home")
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(HomeEvent.OnSignOutClick)
                        }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sign Out"
                        )
                    }
                }
            )
        }
    ) {
    }
}