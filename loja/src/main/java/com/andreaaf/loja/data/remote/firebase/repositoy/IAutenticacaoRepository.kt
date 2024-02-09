package com.andreaaf.loja.data.remote.firebase.repositoy

import com.andreaaf.loja.domain.model.Usuario

interface IAutenticacaoRepository {

    suspend fun cadastrarUsuario(usuario: Usuario) : Boolean
    suspend fun logarUsuario(usuario: Usuario) : Boolean
    suspend fun usuarioEstaLogado() : Boolean //isUserLogged
}