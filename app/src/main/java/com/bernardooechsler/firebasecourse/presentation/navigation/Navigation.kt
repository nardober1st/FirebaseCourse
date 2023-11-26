package com.bernardooechsler.firebasecourse.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroEvent
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroScreen
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroViewModel
import com.bernardooechsler.firebasecourse.presentation.main.MainEvent
import com.bernardooechsler.firebasecourse.presentation.main.MainScreen
import com.bernardooechsler.firebasecourse.presentation.main.MainViewModel
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginViewModel

@Composable
fun Navigation() {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState = loginViewModel.loginState

    val cadastroViewModel: CadastroViewModel = hiltViewModel()
    val cadastroState = cadastroViewModel.cadastroState

    val mainViewModel: MainViewModel = hiltViewModel()
    val homeState = mainViewModel.mainState

    val navController = rememberNavController()

    val scaffoldState = remember {
        SnackbarHostState()
    }

//    val user = firebaseAuth.currentUser

    val isUserSignedIn = mainViewModel.isUserSignedIn()

    NavHost(
        navController = navController,
        startDestination = isUserSignedIn
    ) {
        composable("loginScreen") {
            LaunchedEffect(true) {
                loginViewModel.loginChannelEvent.collect { event ->
                    when (event) {
                        is LoginEvent.LoginClick -> {
                            navController.navigate(Route.HomeScreen.route)
                            loginViewModel.resetFields()
                        }

                        is LoginEvent.SignUpClick -> {
                            navController.navigate(Route.CadastroScreen.route)
                            loginViewModel.resetTextFields()
                        }

                        else -> {}
                    }
                }
            }
            LoginScreen(
                snackBar = scaffoldState,
                state = loginState,
                onEvent = loginViewModel::onEvent
            )
        }
        composable("cadastroScreen") {
            LaunchedEffect(true) {
                cadastroViewModel.cadastroChannelEvent.collect { event ->
                    when (event) {
                        is CadastroEvent.CadastroClick -> {
                            navController.popBackStack()
                        }

                        else -> {}
                    }
                }
            }
            CadastroScreen(
                snackBar = scaffoldState,
                state = cadastroState,
                onEvent = cadastroViewModel::onEvent
            )
        }
        composable("homeScreen") {
            LaunchedEffect(true) {
                mainViewModel.homeChannelEvent.collect { event ->
                    when (event) {
                        is MainEvent.OnSignOutClick -> {
                            navController.navigate(Route.LoginScreen.route)
                        }
                    }
                }
            }
            MainScreen(
                snackBar = scaffoldState,
                state = homeState,
                onEvent = mainViewModel::onEvent
            )
        }
    }
}