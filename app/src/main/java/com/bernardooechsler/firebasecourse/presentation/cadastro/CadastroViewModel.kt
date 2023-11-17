package com.bernardooechsler.firebasecourse.presentation.cadastro

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import com.bernardooechsler.firebasecourse.domain.use_cases.ValidateCadastro
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class CadastroViewModel @Inject constructor(
//    private val validateCadastro: ValidateCadastro,
//    private val repository: AuthRepositoryImpl
//) : ViewModel() {
//
//    var cadastroState by mutableStateOf(CadastroState())
//        private set
//
//    private var _cadastroChannelEvent = Channel<CadastroEvent>()
//    var cadastroChannelEvent = _cadastroChannelEvent.receiveAsFlow()
//
////    val result = validateCadastro(
////        onEmailText = cadastroState.email,
////        onPasswordText = cadastroState.password
////    )
//
//    fun onEvent(event: CadastroEvent) {
//        when (event) {
//            is CadastroEvent.EmailChanged -> {
//                cadastroState = cadastroState.copy(
//                    email = event.email
//                )
//                cadastroState = cadastroState.copy(
//                    isError = null
//                )
//                printState()
//            }
//
//            is CadastroEvent.PasswordChanged -> {
//                cadastroState = cadastroState.copy(
//                    password = event.password
//                )
//                cadastroState = cadastroState.copy(
//                    isError = null
//                )
//                printState()
//            }
//
//            is CadastroEvent.CadastroClick -> {
//                viewModelScope.launch {
//                    repository.registerUser(cadastroState.email, cadastroState.password)
//                        .collect { authResult ->
//                            when (authResult) {
//                                is Resource.Success -> {
//                                    _cadastroChannelEvent.send(CadastroEvent.CadastroClick)
//                                    cadastroState = cadastroState.copy(isLoading = false)
//                                }
//
//                                is Resource.Loading -> {
//                                    cadastroState = cadastroState.copy(
//                                        isLoading = true
//                                    )
//                                }
//
//                                is Resource.Error -> {
//                                    cadastroState = cadastroState.copy(
//                                        isError = authResult.message
//                                    )
//                                    cadastroState = cadastroState.copy(
//                                        isLoading = false
//                                    )
//                                }
//
//                                else -> {}
//                            }
//                        }
//                }
//            }
//
//            else -> {}
//        }
//    }
//
//    private fun printState() {
//        Log.d("TAGY", "Inside_printState")
//        Log.d("TAGY", cadastroState.toString())
//    }
//
//    private fun signUp() {
//        Log.d("TAGY", "Inside_signup")
//        printState()
//        // Implement sign-up logic here
//    }
//}

@HiltViewModel
class CadastroViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    var cadastroState by mutableStateOf(CadastroState())
        private set

    private val _cadastroChannelEvent = Channel<CadastroEvent>()
    val cadastroChannelEvent = _cadastroChannelEvent.receiveAsFlow()

    fun onEvent(event: CadastroEvent) {
        when (event) {
            is CadastroEvent.EmailChanged -> {
                cadastroState = cadastroState.copy(email = event.email, isError = null)
                printState()
            }

            is CadastroEvent.PasswordChanged -> {
                cadastroState = cadastroState.copy(password = event.password, isError = null)
                printState()
            }

            is CadastroEvent.CadastroClick -> {
                onCadastroClick()
                printState()
            }
        }
    }

    private fun onCadastroClick() {
        viewModelScope.launch {
            val result =
                repository.registerUser(cadastroState.email, cadastroState.password).collect {
                    handleAuthResult(it)
                }
        }
    }

    private fun handleAuthResult(authResult: Resource<AuthResult>) {

        when (authResult) {
            is Resource.Success -> {
                viewModelScope.launch {
                    _cadastroChannelEvent.send(CadastroEvent.CadastroClick)
                    cadastroState = cadastroState.copy(
                        isLoading = false,
                        isSuccess = true)
                }
            }

            is Resource.Loading -> {
                cadastroState = cadastroState.copy(isLoading = true)
            }

            is Resource.Error -> {
                cadastroState = cadastroState.copy(
                    isError = authResult.message
                )
                cadastroState = cadastroState.copy(
                    isLoading = false
                )
            }

            else -> { /* Handle other cases if needed */
            }
        }
    }

    private fun printState() {
        // Logging state changes
        Log.d("TAGY", "Inside_printState")
        Log.d("TAGY", cadastroState.toString())
    }
}