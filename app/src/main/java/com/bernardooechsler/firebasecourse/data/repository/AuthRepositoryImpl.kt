package com.bernardooechsler.firebasecourse.data.repository

import com.bernardooechsler.firebasecourse.data.model.User
import com.bernardooechsler.firebasecourse.domain.repository.AuthRepository
import com.bernardooechsler.firebasecourse.presentation.navigation.rootnavgraph.RootGraphRoutes
import com.bernardooechsler.firebasecourse.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun loginUser(user: User, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS
            val isValidEmail = emailPattern.matcher(user.email).matches()
            if (user.email.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Email and password cannot be empty!"))
            } else if (!isValidEmail) {
                emit(Resource.Error("Invalid email format!"))
            } else {
                emit(Resource.Loading())

                try {
                    val result =
                        firebaseAuth.signInWithEmailAndPassword(user.email, password).await()
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


    override fun registerUser(user: User, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS
            val isValidEmail = emailPattern.matcher(user.email).matches()
            if (user.email.isEmpty() || password.isEmpty() || user.name?.isEmpty() == true) {
                emit(Resource.Error("Fields cannot be empty!"))
            } else if (!isValidEmail) {
                emit(Resource.Error("Invalid email format!"))
            } else {
                emit(Resource.Loading())

                try {
                    val result =
                        firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()

                    val db = firestore.collection("users").document(result.user?.uid ?: "")
                    val userData = hashMapOf(
                        "email" to user.email, "name" to user.name
                    )
                    db.set(userData).await()

                    emit(Resource.Success(result))
                } catch (e: FirebaseAuthWeakPasswordException) {
                    emit(Resource.Error("Weak password!"))
                } catch (e: FirebaseNetworkException) {
                    emit(Resource.Error("No Internet Connection!"))
                } catch (e: FirebaseAuthUserCollisionException) {
                    emit(Resource.Error("User already exists"))
                } catch (e: Exception) {
                    emit(Resource.Error("Unknown error occurred"))
                }
            }
        }
    }

    override fun isUserSignedIn(): String {
        val user = firebaseAuth.currentUser?.uid
        return if (user != null) {
            RootGraphRoutes.MainGraphRoute.route
        } else {
            RootGraphRoutes.AuthGraphRoute.route
        }
    }

    override fun signUserOut() {
        firebaseAuth.signOut()
    }

    override fun recoverPassword(user: User) {
        firebaseAuth.sendPasswordResetEmail(user.email)
    }
}
