package com.bernardooechsler.firebasecourse.presentation.navigation

sealed class Route(val route: String) {
    object LoginScreen : Route(route = "loginScreen")
    object CadastroScreen : Route(route = "cadastroScreen")
    object HomeScreen : Route(route = "homeScreen")
}
