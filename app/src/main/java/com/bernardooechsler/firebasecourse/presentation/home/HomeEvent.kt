package com.bernardooechsler.firebasecourse.presentation.home

sealed class HomeEvent {
    object OnSignOutClick : HomeEvent()
}