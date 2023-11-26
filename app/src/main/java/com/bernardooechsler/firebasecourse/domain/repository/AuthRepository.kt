package com.bernardooechsler.firebasecourse.domain.repository

import com.bernardooechsler.firebasecourse.data.model.User
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(user: User, password: String): Flow<Resource<AuthResult>>
    fun registerUser(user: User, password: String): Flow<Resource<AuthResult>>
    fun isUserSignedIn(): String
    fun signUserOut()
    fun recoverPassword(user: User)
}