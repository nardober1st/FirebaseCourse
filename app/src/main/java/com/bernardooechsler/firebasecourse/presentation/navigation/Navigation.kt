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
import com.bernardooechsler.firebasecourse.presentation.home.HomeScreen
import com.bernardooechsler.firebasecourse.presentation.home.HomeViewModel
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation() {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState = loginViewModel.loginState

    val cadastroViewModel: CadastroViewModel = hiltViewModel()
    val cadastroState = cadastroViewModel.cadastroState

    val homeViewModel: HomeViewModel = hiltViewModel()

    val navController = rememberNavController()

    val scaffoldState = remember {
        SnackbarHostState()
    }

//    val user = firebaseAuth.currentUser

    val isUserSignedIn = homeViewModel.isUserSignedIn()

    NavHost(
        navController = navController,
        startDestination = isUserSignedIn
    ) {
        composable("loginScreen") {
            LaunchedEffect(true) {
                loginViewModel.loginChannelEvent.collect { event ->
                    when (event) {
                        is LoginEvent.LoginClick -> {
                            navController.navigate(Route.HomeScreen.route) {
                                popUpTo(Route.LoginScreen.route) {
                                    inclusive = true
                                }
                            }
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
            HomeScreen(navController = navController)
        }
    }
}