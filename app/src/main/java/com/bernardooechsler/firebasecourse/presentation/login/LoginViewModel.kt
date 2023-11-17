package com.bernardooechsler.firebasecourse.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardooechsler.firebasecourse.data.model.User
import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    private var _loginChannelEvent = Channel<LoginEvent>()
    var loginChannelEvent = _loginChannelEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {

            when (event) {
                is LoginEvent.EmailChanged -> {
                    loginState = loginState.copy(
                        email = event.email,
                        isError = null
                    )
                    printState()
                }

                is LoginEvent.PasswordChanged -> {
                    loginState = loginState.copy(
                        password = event.password,
                        isError = null
                    )
                    printState()
                }

                is LoginEvent.LoginClick -> {
                    onLoginClick()
                    login()
                }

                is LoginEvent.SignUpClick -> {
                    loginState = loginState.copy(
                        isLoginClicked = false,
                        isCadastroClicked = true
                    )
                    _loginChannelEvent.send(LoginEvent.SignUpClick)
                    signUp()
                }
            }
        }
    }

    private fun onLoginClick() {
        loginState = loginState.copy(
            isLoginClicked = true
        )
        val user = User(
            email = loginState.email,
            name = null
        )
        viewModelScope.launch {
            repository.loginUser(user, loginState.password).collect {
                handleAuthResult(it)
            }
        }
    }

    private fun handleAuthResult(authResult: Resource<AuthResult>) {
        when (authResult) {
            is Resource.Success -> {
                viewModelScope.launch {
                    _loginChannelEvent.send(LoginEvent.LoginClick)
                    loginState = loginState.copy(
                        isLoggedIn = true,
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }

            is Resource.Loading -> {
                loginState = loginState.copy(isLoading = true)
            }

            is Resource.Error -> {
                loginState = loginState.copy(
                    isError = authResult.message
                )
                loginState = loginState.copy(
                    isLoading = false
                )
            }

            else -> {}
        }
    }

    private fun printState() {
        Log.d("TAGY", "Inside_printState")
        Log.d("TAGY", loginState.toString())
    }

    private fun login() {
        Log.d("TAGY", "Inside_login")
        printState()
    }

    private fun signUp() {
        Log.d("TAGY", "Inside_signup")
        printState()
    }

    fun resetTextFields() {
        loginState = loginState.copy(
            email = "",
            password = ""
        )
    }
}