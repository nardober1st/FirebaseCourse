package com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroEvent
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroScreen
import com.bernardooechsler.firebasecourse.presentation.cadastro.CadastroViewModel
import com.bernardooechsler.firebasecourse.presentation.main.MainScreen
import com.bernardooechsler.firebasecourse.presentation.main.MainViewModel
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginScreen
import com.bernardooechsler.firebasecourse.presentation.login.LoginViewModel
import com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph.AuthRoutes
import com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph.authNavGraph

@Composable
fun RootNavigationGraph(navController: NavHostController) {

//    val loginViewModel: LoginViewModel = hiltViewModel()
//    val loginState = loginViewModel.loginState
//
//    val cadastroViewModel: CadastroViewModel = hiltViewModel()
//    val cadastroState = cadastroViewModel.cadastroState

    val mainViewModel: MainViewModel = hiltViewModel()
    val mainState = mainViewModel.mainState

    val navController = rememberNavController()

    val scaffoldState = remember {
        SnackbarHostState()
    }

    val isUserSignedIn = mainViewModel.isUserSignedIn()

    NavHost(
        navController = navController,
        route = RootGraphRoutes.RootGraphRoute.route,
        startDestination = isUserSignedIn
    ) {
        authNavGraph(navController)
//        navigation(
//            route = RootGraphRoutes.AuthGraphRoute.route,
//            startDestination = AuthRoutes.LoginRoute.route
//        ) {
//            composable(route = AuthRoutes.LoginRoute.route) {
//                LaunchedEffect(true) {
//                    loginViewModel.loginChannelEvent.collect { event ->
//                        when (event) {
//                            is LoginEvent.LoginClick -> {
//                                navController.popBackStack()
//                                navController.navigate(RootGraphRoutes.MainGraphRoute.route)
//                                loginViewModel.resetFields()
//                            }
//
//                            is LoginEvent.SignUpClick -> {
//                                navController.navigate(AuthRoutes.SignupRoute.route)
//                                loginViewModel.resetTextFields()
//                            }
//
//                            else -> {}
//                        }
//                    }
//                }
//                LoginScreen(
//                    snackBar = scaffoldState,
//                    state = loginState,
//                    onEvent = loginViewModel::onEvent
//                )
//            }
//            composable(route = AuthRoutes.SignupRoute.route) {
//                LaunchedEffect(true) {
//                    cadastroViewModel.cadastroChannelEvent.collect { event ->
//                        when (event) {
//                            is CadastroEvent.CadastroClick -> {
//                                navController.popBackStack()
//                            }
//
//                            else -> {}
//                        }
//                    }
//                }
//                CadastroScreen(
//                    snackBar = scaffoldState,
//                    state = cadastroState,
//                    onEvent = cadastroViewModel::onEvent
//                )
//            }
//        }
        composable(route = RootGraphRoutes.MainGraphRoute.route) {
            MainScreen(
                snackBar = scaffoldState,
                state = mainState,
                onEvent = mainViewModel::onEvent
            )
        }
    }
}

//object Graph {
//    const val ROOT = "root_graph"
//    const val AUTHENTICATION = "auth_graph"
//    const val MAIN = "main_graph"
//    const val HOME = "home_graph"
//}