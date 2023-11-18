package com.andreaaf.appifood.data.remote.firebase.repository

import com.andreaaf.appifood.domain.model.Usuario

interface IAutenticacaoRepository {

    suspend fun cadastrarUsuario(usuario: Usuario) : Boolean
    suspend fun logarUsuario(usuario: Usuario) : Boolean
    suspend fun usuarioEstaLogado() : Boolean //isUserLogged
}