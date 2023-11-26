package com.bernardooechsler.firebasecourse.presentation.main

sealed class MainEvent {
    object OnSignOutClick : MainEvent()
}