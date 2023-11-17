package com.bernardooechsler.firebasecourse.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    private var _loginChannelEvent = Channel<LoginEvent>()
    var loginChannelEvent = _loginChannelEvent.receiveAsFlow()
//
//    private var _loginSharedFlowEvent = MutableSharedFlow<LoginEvent>()
//    var loginSharedFlowEvent = _loginSharedFlowEvent.asSharedFlow()

//    var isLoggedIn by mutableStateOf(false)
//        private set

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {

            when (event) {
                is LoginEvent.EmailChanged -> {
                    loginState = loginState.copy(
                        email = event.email
                    )
                    printState()
                }

                is LoginEvent.PasswordChanged -> {
                    loginState = loginState.copy(
                        password = event.password
                    )
                    printState()
                }

                is LoginEvent.LoginClick -> {
//                viewModelScope.launch {
                    loginState = loginState.copy(
                        isLoginClicked = true,
                        isLoggedIn = true,
                        isCadastroClicked = false
                    )
                    _loginChannelEvent.send(LoginEvent.LoginClick)
                    login()
//            }
                }

                is LoginEvent.SignUpClick -> {
//            viewModelScope.launch {
                    loginState = loginState.copy(
                        isLoginClicked = false,
                        isCadastroClicked = true
                    )
                    _loginChannelEvent.send(LoginEvent.SignUpClick)
                    signUp()
//            }
                }
            }
        }
    }

    private fun printState() {
        Log.d("TAGY", "Inside_printState")
        Log.d("TAGY", loginState.toString())
    }

    private fun login() {
        Log.d("TAGY", "Inside_login")
        printState()
        // Implement login logic here
    }

    private fun signUp() {
        Log.d("TAGY", "Inside_signup")
        printState()
        // Implement sign-up logic here
    }
}