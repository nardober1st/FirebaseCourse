package com.bernardooechsler.firebasecourse.presentation.navigation.homenavgraph

sealed class HomeScreen(val route: String) {
    object Videos : HomeScreen(route = "VIDEOS")
    object Graph : HomeScreen(route = "GRAPH")
}