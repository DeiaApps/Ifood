package com.andreaaf.domain.usecase

import com.andreaaf.appifood.domain.model.Usuario

interface IAutenticacaoRepository {

    suspend fun cadastrarUsuario(usuario: Usuario) : Boolean
    suspend fun logarUsuario(usuario: Usuario) : Boolean
    suspend fun usuarioEstaLogado() : Boolean //isUserLogged
}