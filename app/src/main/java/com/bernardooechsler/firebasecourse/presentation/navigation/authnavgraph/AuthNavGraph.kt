package com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroEvent
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroScreen
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroViewModel
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginViewModel
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootGraphRoutes

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = RootGraphRoutes.AuthGraphRoute.route,
        startDestination = AuthRoutes.LoginRoute.route
    ) {
        composable(route = AuthRoutes.LoginRoute.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginState = loginViewModel.loginState

            val scaffoldState = remember {
                SnackbarHostState()
            }
            LaunchedEffect(true) {
                loginViewModel.loginChannelEvent.collect { event ->
                    when (event) {
                        is LoginEvent.LoginClick -> {
                            navController.popBackStack()
                            navController.navigate(RootGraphRoutes.MainGraphRoute.route)
                            loginViewModel.resetFields()
                        }

                        is LoginEvent.SignUpClick -> {
                            navController.navigate(AuthRoutes.SignupRoute.route)
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
        composable(route = AuthRoutes.SignupRoute.route) {
            val cadastroViewModel: CadastroViewModel = hiltViewModel()
            val cadastroState = cadastroViewModel.cadastroState

            val scaffoldState = remember {
                SnackbarHostState()
            }
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
    }
}