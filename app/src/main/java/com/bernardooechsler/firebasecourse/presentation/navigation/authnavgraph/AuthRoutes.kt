package com.bernardooechsler.firebasecourse.presentation.navigation.authnavgraph

sealed class AuthRoutes(val route: String) {
    object LoginRoute : AuthRoutes(route = "login")
    object SignupRoute : AuthRoutes(route = "signup")
}
