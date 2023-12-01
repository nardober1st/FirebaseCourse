package com.bernardooechsler.firebasecourse.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    var mainState by mutableStateOf(MainState())
        private set

    private var _homeChannelEvent = Channel<MainEvent>()
    var homeChannelEvent = _homeChannelEvent.receiveAsFlow()

    fun isUserSignedIn(): Boolean {
        return repository.isUserSignedIn()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnSignOutClick -> {
                mainState = mainState.copy(
                    isSignOutClicked = true
                )
                onSignUserOut()
            }
        }
    }

    private fun onSignUserOut() {
        viewModelScope.launch {
            repository.signUserOut()
            _homeChannelEvent.send(MainEvent.OnSignOutClick)
        }
    }
}