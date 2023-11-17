package com.bernardooechsler.firebasecourse.di

import com.bernardooechsler.firebasecourse.data.repository.AuthRepositoryImpl
import com.bernardooechsler.firebasecourse.domain.repository.AuthRepository
import com.bernardooechsler.firebasecourse.domain.use_cases.ValidateCadastro
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideValidateNutrientsUseCase(): ValidateCadastro {
        return ValidateCadastro()
    }
}