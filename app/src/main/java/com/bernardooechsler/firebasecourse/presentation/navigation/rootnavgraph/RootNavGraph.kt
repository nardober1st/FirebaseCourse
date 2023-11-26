package com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bernardooechsler.firebasecourse.presentation.main.MainScreen
import com.bernardooechsler.firebasecourse.presentation.main.MainViewModel
import com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph.authNavGraph

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    val mainViewModel: MainViewModel = hiltViewModel()
    val mainState = mainViewModel.mainState

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
        composable(route = RootGraphRoutes.MainGraphRoute.route) {
            MainScreen(
                snackBar = scaffoldState,
                state = mainState,
                onEvent = mainViewModel::onEvent
            )
        }
    }
}