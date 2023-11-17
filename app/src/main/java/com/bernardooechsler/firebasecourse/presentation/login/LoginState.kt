package com.bernardooechsler.firebasecourse.presentation.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var isLoginClicked: Boolean = false,
    var isCadastroClicked: Boolean = false,
    var isLoggedIn: Boolean = false
)