package com.bernardooechsler.firebasecourse.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bernardooechsler.firebasecourse.R
import com.bernardooechsler.firebasecourse.presentation.navigation.Route
import com.bernardooechsler.firebasecourse.ui.theme.LightGrey
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginScreen(
//    navController: NavController,
//    state: LoginState,
//    onEvent: (LoginEvent) -> Unit
//) {
////    val viewModel: LoginViewModel = viewModel()
////    val state = viewModel.loginState
//
////    LaunchedEffect(state.isLoggedIn) {
////        viewModel.loginSharedFlowEvent.collect() { event ->
////            when (event) {
////                is LoginEvent.LoginClick -> {
////                    navController.navigate(Route.HomeScreen.route)
////                }
////
////                is LoginEvent.SignUpClick -> {
////                    navController.navigate(Route.CadastroScreen.route)
////                }
////
////                else -> {}
////            }
////        }
////    }
//
//    // this way, we can make sure that when user is Loggedin, it wont be able to popback to login screen
//    LaunchedEffect(state.isLoggedIn) {
//        if (state.isLoggedIn) {
//            navController.navigate(Route.HomeScreen.route)
//        }
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Text(
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 80.dp),
//            text = "Bem Vindo!",
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp
//        )
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            OutlinedTextField(
//                modifier = Modifier,
//                value = state.email,
//                onValueChange = {
////                    viewModel.onEvent(LoginEvent.EmailChanged(it))
//                    onEvent(LoginEvent.EmailChanged(it))
//                },
//                label = {
//                    Text(text = "Email")
//                }
//            )
//            OutlinedTextField(
//                modifier = Modifier
//                    .padding(top = 10.dp),
//                value = state.password,
//                onValueChange = {
////                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
//                    onEvent(LoginEvent.PasswordChanged(it))
//                },
//                label = {
//                    Text(text = "Senha")
//                }
//            )
//            Text(
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .padding(end = 40.dp),
//                text = "Esqueceu a senha?"
//            )
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 40.dp, end = 40.dp, top = 40.dp),
//                shape = RoundedCornerShape(4.dp),
//                onClick = {
////                    viewModel.onEvent(LoginEvent.LoginClick)
//                    onEvent(LoginEvent.LoginClick)
////                    navController.navigate(Route.HomeScreen.route)
//                }) {
//                Text(text = "Log In")
//            }
//            Text(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(top = 6.dp),
//                text = "Or"
//            )
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 40.dp, end = 40.dp, top = 6.dp),
//                shape = RoundedCornerShape(4.dp),
//                onClick = {
////                    viewModel.onEvent(LoginEvent.SignUpClick)
//                    onEvent(LoginEvent.SignUpClick)
//                    navController.navigate(Route.CadastroScreen.route)
//                }) {
//                Text(text = "Sign Up")
//            }
//        }
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    snackBar: SnackbarHostState,
//    onLoginClick: () -> Unit,
//    onSignupClick: () -> Unit,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {

    val savedState = rememberSaveableStateHolder()
    val scope = rememberCoroutineScope()

    // this way, we can make sure that when user is Loggedin, it wont be able to popback to login screen
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onEvent(LoginEvent.LoginClick)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
//                    viewModel.onEvent(LoginEvent.EmailChanged(it))
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
//                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
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
//                    viewModel.onEvent(LoginEvent.LoginClick)
                        onEvent(LoginEvent.LoginClick)
//                    navController.navigate(Route.HomeScreen.route)
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
//                    viewModel.onEvent(LoginEvent.SignUpClick)
                        onEvent(LoginEvent.SignUpClick)
//                        onSignupClick()
                    }) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}