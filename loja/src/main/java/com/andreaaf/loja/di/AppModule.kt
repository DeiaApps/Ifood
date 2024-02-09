package com.andreaaf.loja.di

import com.andreaaf.loja.data.remote.firebase.repositoy.AutenticacaoRepositoryImpl
import com.andreaaf.loja.data.remote.firebase.repositoy.IAutenticacaoRepository
import com.andreaaf.loja.data.remote.firebase.repositoy.ILojaRepository
import com.andreaaf.loja.data.remote.firebase.repositoy.LojaRepositoryImpl
import com.andreaaf.loja.domain.usecase.AutenticacaoUseCase
import com.andreaaf.loja.domain.usecase.LojaUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    fun provideLojaUseCase(
        lojaRepositoryImpl: ILojaRepository
    ) : LojaUseCase {
        return LojaUseCase( lojaRepositoryImpl )
    }

    @Provides
    fun provideAutenticacaoRepositoryImpl(
        firebaseAuth: FirebaseAuth
    ) : AutenticacaoRepositoryImpl {
        return AutenticacaoRepositoryImpl( firebaseAuth )
    }

    @Provides
    fun provideLojaRepositoryImpl(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ) : LojaRepositoryImpl {
        return LojaRepositoryImpl( firestore, storage )
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

}