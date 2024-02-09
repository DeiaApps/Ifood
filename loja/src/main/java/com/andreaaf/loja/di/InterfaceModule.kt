package com.andreaaf.loja.di

import com.andreaaf.loja.data.remote.firebase.repositoy.AutenticacaoRepositoryImpl
import com.andreaaf.loja.data.remote.firebase.repositoy.IAutenticacaoRepository
import com.andreaaf.loja.data.remote.firebase.repositoy.ILojaRepository
import com.andreaaf.loja.data.remote.firebase.repositoy.LojaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn( ViewModelComponent::class)//JAVA: ViewModelComponent.java
abstract class InterfaceModule {

    //Conectar a Interface com a classe concreta
    @Binds
    abstract fun bindIAutenticacaoRepository(
        autenticacaoRepositoryImpl: AutenticacaoRepositoryImpl
    ) : IAutenticacaoRepository

    @Binds
    abstract fun bindILojaRepository(
        lojaRepositoryImpl: LojaRepositoryImpl
    ) : ILojaRepository
}