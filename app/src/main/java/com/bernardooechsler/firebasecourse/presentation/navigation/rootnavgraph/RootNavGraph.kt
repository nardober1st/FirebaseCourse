package com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bernardooechsler.firebasecourse.presentation.main.MainEvent
import com.bernardooechsler.firebasecourse.presentation.main.MainScreen
import com.bernardooechsler.firebasecourse.presentation.main.MainViewModel
import com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph.authNavGraph

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    val mainViewModel: MainViewModel = hiltViewModel()

    val scaffoldState = remember {
        SnackbarHostState()
    }

    val isUserSignedIn = mainViewModel.isUserSignedIn()

    LaunchedEffect(isUserSignedIn) {
        mainViewModel.homeChannelEvent.collect { event ->
            when (event) {
                is MainEvent.OnSignOutClick -> {
                    navController.navigate(RootGraphRoutes.AuthGraphRoute.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        route = RootGraphRoutes.RootGraphRoute.route,
        startDestination = if (isUserSignedIn) RootGraphRoutes.MainGraphRoute.route else RootGraphRoutes.AuthGraphRoute.route
    ) {
        authNavGraph(navController)
        composable(route = RootGraphRoutes.MainGraphRoute.route) {
            MainScreen(
                snackBar = scaffoldState,
                onEvent = mainViewModel::onEvent
            )
        }
    }
}