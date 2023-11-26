package com.bernardooechsler.firebasecourse.presentation.navigation.homenavgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bernardooechsler.firebasecourse.presentation.GraphScreen
import com.bernardooechsler.firebasecourse.presentation.VideosScreen
import com.bernardooechsler.firebasecourse.presentation.navigation.mainnavgraph.BottomBarScreen
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootGraphRoutes

// THIS WE CAN USE IF on home screen we have something that take us to another screen, and that screen will have its own navigation
fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = RootGraphRoutes.HomeGraphRoute.route,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = HomeScreen.Videos.route) {
            VideosScreen()
        }
        composable(route = HomeScreen.Graph.route) {
            GraphScreen()
        }
    }
}