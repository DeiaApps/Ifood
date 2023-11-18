package com.andreaaf.appifood.di

import com.andreaaf.appifood.data.remote.firebase.repository.AutenticacaoRepositoryImpl
import com.andreaaf.appifood.data.remote.firebase.repository.IAutenticacaoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn( ViewModelComponent::class)//JAVA: ViewModelComponent.java
abstract class InterfaceModule {

    //Conectar a Interce com a classe concreta
    @Binds
    abstract fun bindIAutenticacaoRepository(
        autenticacaoRepositoryImpl: AutenticacaoRepositoryImpl
    ) : IAutenticacaoRepository
}