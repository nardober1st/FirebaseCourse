package com.bernardooechsler.firebasecourse.presentation.navigation.mainnavgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bernardooechsler.firebasecourse.presentation.home.HomeScreen
import com.bernardooechsler.firebasecourse.presentation.navigation.homenavgraph.HomeScreen
import com.bernardooechsler.firebasecourse.presentation.navigation.homenavgraph.homeNavGraph
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootGraphRoutes
import com.bernardooechsler.firebasecourse.presentation.profile.ProfileScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = RootGraphRoutes.MainGraphRoute.route,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                onGraphClick = { navController.navigate(HomeScreen.Graph.route) },
                onVideoClick = { navController.navigate(HomeScreen.Videos.route) }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        homeNavGraph(navController)
    }
}