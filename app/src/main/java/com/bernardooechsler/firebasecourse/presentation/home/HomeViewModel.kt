package com.bernardooechsler.firebasecourse.presentation.home

import androidx.lifecycle.ViewModel
import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    fun isUserSignedIn(): String {
        return repository.isUserSignedIn()
    }
}