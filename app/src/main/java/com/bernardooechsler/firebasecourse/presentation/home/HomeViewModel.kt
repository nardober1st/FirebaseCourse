package com.bernardooechsler.firebasecourse.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import com.bernardooechsler.firebasecourse.presentation.login.LoginEvent
import com.bernardooechsler.firebasecourse.presentation.login.LoginState
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    private var _homeChannelEvent = Channel<HomeEvent>()
    var homeChannelEvent = _homeChannelEvent.receiveAsFlow()

    fun isUserSignedIn(): String {
        return repository.isUserSignedIn()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSignOutClick -> {
                homeState = homeState.copy(
                    isSignOutClicked = true
                )
                onSignUserOut()
            }
        }
    }

    private fun onSignUserOut() {
        viewModelScope.launch {
            repository.signUserOut()
            homeState = homeState.copy(
                isSignOutClicked = false
            )
        }
    }
}