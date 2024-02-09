package com.andreaaf.appifood.di

import com.andreaaf.appifood.data.remote.firebase.repository.AutenticacaoRepositoryImpl
import com.andreaaf.appifood.data.remote.firebase.repository.IAutenticacaoRepository
import com.andreaaf.appifood.domain.usecase.AutenticacaoUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn( ViewModelComponent::class)//JAVA: ViewModelComponent.java
object AppModule {//JAVA: Utiliza uma classe normal

    @Provides
    fun provideAutenticacaoUseCase(
        autenticacaoRepositoryImpl: IAutenticacaoRepository
    ): AutenticacaoUseCase {
        return AutenticacaoUseCase( autenticacaoRepositoryImpl )
    }

    @Provides
    fun provideAutenticacaoRepositoryImpl( firebaseAuth: FirebaseAuth ) : AutenticacaoRepositoryImpl{
        return AutenticacaoRepositoryImpl( firebaseAuth )
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
}