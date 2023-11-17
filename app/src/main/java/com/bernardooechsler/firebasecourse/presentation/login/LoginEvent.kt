package com.bernardooechsler.firebasecourse.presentation.login

sealed class LoginEvent {
    data class EmailChanged(val email: String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    object LoginClick : LoginEvent()
    object SignUpClick : LoginEvent()
}