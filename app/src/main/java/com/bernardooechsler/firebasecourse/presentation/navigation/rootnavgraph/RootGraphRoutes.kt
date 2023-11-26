package com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph

sealed class RootGraphRoutes(val route: String) {
    object RootGraphRoute : RootGraphRoutes(route = "root")
    object AuthGraphRoute : RootGraphRoutes(route = "auth")
    object MainGraphRoute : RootGraphRoutes(route = "main")
    object HomeGraphRoute : RootGraphRoutes(route = "home")
}

