package com.bernardooechsler.firebasecourse.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    snackBar: SnackbarHostState,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {

    val savedState = rememberSaveableStateHolder()
    val scope = rememberCoroutineScope()

//    LaunchedEffect(state.isLoggedIn) {
//        if (state.isLoggedIn) {
//            onEvent(LoginEvent.LoginClick)
//        }
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBar)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp),
                text = "Bem Vindo!",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    modifier = Modifier,
                    value = state.email,
                    onValueChange = {
                        onEvent(LoginEvent.EmailChanged(it))
                    },
                    label = {
                        Text(text = "Email")
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    value = state.password,
                    onValueChange = {
                        onEvent(LoginEvent.PasswordChanged(it))
                    },
                    label = {
                        Text(text = "Senha")
                    }
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 40.dp),
                    text = "Esqueceu a senha?"
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 40.dp),
                    shape = RoundedCornerShape(4.dp),
                    onClick = {
                        onEvent(LoginEvent.LoginClick)
                    }) {
                    Text(text = "Log In")
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 6.dp),
                    text = "Or"
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, top = 6.dp),
                    shape = RoundedCornerShape(4.dp),
                    onClick = {
                        onEvent(LoginEvent.SignUpClick)
                    }) {
                    Text(text = "Sign Up")
                }

                when {
                    state.isLoading -> {
                        Dialog(
                            onDismissRequest = { state.isLoading = false },
                            DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false
                            )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    state.isError?.isNotEmpty() == true -> {
                        scope.launch {
                            snackBar.showSnackbar(state.isError!!)
                        }
                    }

                    state.isSuccess -> {
                        scope.launch {
                            snackBar.showSnackbar(
                                "Login feito com sucesso!",
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                }
            }
        }
    }
}