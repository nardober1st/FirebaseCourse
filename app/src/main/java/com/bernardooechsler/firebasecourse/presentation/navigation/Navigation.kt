package com.bernardooechsler.firebasecourse.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroEvent
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroScreen
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroState
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroViewModel
import com.bernardooechsler.firebasecourse.presentation.home.HomeScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val scaffoldState = remember {
        SnackbarHostState()
    }

    NavHost(
        navController = navController,
        startDestination = Route.LoginScreen.route
    ) {
        composable("loginScreen") {
            val viewModel: LoginViewModel = viewModel()
            var state = viewModel.loginState

            LaunchedEffect(true) {
                viewModel.loginChannelEvent.collect { event ->
                    when (event) {
                        is LoginEvent.LoginClick -> {
                            navController.navigate(Route.HomeScreen.route)
                        }

                        is LoginEvent.SignUpClick -> {
                            navController.navigate(Route.CadastroScreen.route)
                        }

                        else -> {}
                    }
                }
            }
            LoginScreen(
                snackBar = scaffoldState,
//                navController = navController,
//                onLoginClick = {
//                    navController.navigate(Route.HomeScreen.route)
//                },
//                onSignupClick = {
//                    navController.navigate(Route.CadastroScreen.route)
//                },
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable("cadastroScreen") {
            val viewModel: CadastroViewModel = hiltViewModel()
            val state = viewModel.cadastroState

            LaunchedEffect(true) {
                viewModel.cadastroChannelEvent.collect { event ->
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
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        composable("homeScreen") {
            HomeScreen(navController = navController)
        }
    }
}