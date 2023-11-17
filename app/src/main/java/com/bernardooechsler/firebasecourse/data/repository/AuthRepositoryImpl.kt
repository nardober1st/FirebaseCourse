package com.bernardooechsler.firebasecourse.data.repository

import com.bernardooechsler.firebasecourse.domain.repository.AuthRepository
import com.bernardooechsler.firebasecourse.domain.use_cases.ValidateCadastro
import com.bernardooechsler.firebasecourse.presentation.navigation.Route
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            if (email.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Email and password cannot be empty!"))
            } else {
                emit(Resource.Loading())

                try {
                    val result =
                        firebaseAuth.signInWithEmailAndPassword(email, password).await()
                    emit(Resource.Success(result))
                } catch (e: FirebaseAuthInvalidUserException) {
                    when (e.errorCode) {
                        "ERROR_USER_DISABLED" -> emit(Resource.Error("User account has been disabled!"))
                        "ERROR_USER_NOT_FOUND" -> emit(Resource.Error("User account not found!"))
                        else -> emit(Resource.Error("Invalid user!"))
                    }
                } catch (e: FirebaseNetworkException) {
                    emit(Resource.Error("No Internet Connection!"))
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    emit(Resource.Error("Invalid credentials!"))
                } catch (e: Exception) {
                    emit(Resource.Error("Unknown error occurred"))
                }
            }
        }
    }


    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            if (email.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Email and password cannot be empty!"))
            } else {
                emit(Resource.Loading())

                try {
                    val result =
                        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                    emit(Resource.Success(result))
                } catch (e: FirebaseAuthWeakPasswordException) {
                    emit(Resource.Error("Weak password!"))
                } catch (e: FirebaseNetworkException) {
                    emit(Resource.Error("No Internet Connection!"))
                } catch (e: FirebaseAuthUserCollisionException) {
                    emit(Resource.Error("User already exists"))
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    emit(Resource.Error("Email format is wrong"))
                } catch (e: Exception) {
                    emit(Resource.Error("Unknown error occurred"))
                }
            }
        }
    }

    override fun isUserSignedIn(): String {
        val user = firebaseAuth.currentUser?.uid
        return if (user != null) {
            Route.HomeScreen.route
        } else {
            Route.LoginScreen.route
        }
    }
}
