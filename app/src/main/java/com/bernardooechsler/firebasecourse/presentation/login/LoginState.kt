package com.bernardooechsler.firebasecourse.presentation.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var emailRecover: String = "",
    var isLoginClicked: Boolean = false,
    var isCadastroClicked: Boolean = false,
    var isSendEmailClick: Boolean = false,
    var isLoading: Boolean = false,
    var isError: String? = "",
    val isSuccess: Boolean = false,
    var isLoggedIn: Boolean = false
)