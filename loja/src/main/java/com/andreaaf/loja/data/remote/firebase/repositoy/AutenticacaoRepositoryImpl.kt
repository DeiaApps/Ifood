package com.andreaaf.loja.data.remote.firebase.repositoy

import android.util.Log
import com.andreaaf.loja.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AutenticacaoRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): IAutenticacaoRepository {
    override suspend fun cadastrarUsuario(usuario: Usuario) : Boolean {
       val resultado = auth.createUserWithEmailAndPassword(
            usuario.email,
            usuario.senha
        ).await() != null
        Log.i("cadastro usuario firebase", "res: $resultado")
        return resultado
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