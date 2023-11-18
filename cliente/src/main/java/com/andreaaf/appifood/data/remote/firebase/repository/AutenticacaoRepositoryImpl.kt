package com.andreaaf.appifood.data.remote.firebase.repository

import com.andreaaf.appifood.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AutenticacaoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): IAutenticacaoRepository {
    override suspend fun cadastrarUsuario(usuario: Usuario) : Boolean {
       return auth.createUserWithEmailAndPassword(
            usuario.email,
            usuario.senha
        ).await() != null

    }

    override suspend fun logarUsuario(usuario: Usuario) : Boolean {
        return auth.signInWithEmailAndPassword(
            usuario.email,
            usuario.senha
        ).await() != null
    }

    override suspend fun usuarioEstaLogado(): Boolean {
        return auth.currentUser != null
    }

}