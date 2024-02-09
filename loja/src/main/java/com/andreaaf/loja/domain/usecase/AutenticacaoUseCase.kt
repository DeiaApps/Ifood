package com.andreaaf.loja.domain.usecase

import com.andreaaf.loja.domain.model.Usuario
import com.andreaaf.loja.data.remote.firebase.repositoy.IAutenticacaoRepository
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import javax.inject.Inject

class AutenticacaoUseCase @Inject constructor(
    private val iAutenticacaoRepository: IAutenticacaoRepository
){
    fun validarCadastroUsuario(usuario: Usuario ) : ResultadoAutenticacao {

        //"andrea"-> true ""-> false
        val resultadoAutenticacao = ResultadoAutenticacao()
        if ( !usuario.nome.nonEmpty())//inválido só qdo não preenchido
            resultadoAutenticacao.nomeInvalido = true//nome não foi preenchido

        if ( !usuario.email.validEmail())
            resultadoAutenticacao.emailInvalido = true//Deu erro, e-mail inválido

        val senha = usuario.senha.validator()
            .nonEmpty()
            .minLength(6)
            .check()
        //if ( !usuario.senha.nonEmpty() && usuario.senha.minLength(6) )
        if ( !senha )
            resultadoAutenticacao.senhaInvalido = true

        if ( !usuario.telefone.nonEmpty() )
            resultadoAutenticacao.telefoneInvalido = true

        return resultadoAutenticacao

    }

    fun validarLoginUsuario( usuario: Usuario ) : ResultadoAutenticacao {
        val resultadoAutenticacao = ResultadoAutenticacao()
        if ( !usuario.email.validEmail())
            resultadoAutenticacao.emailInvalido = true//Deu erro, e-mail inválido

        val senha = usuario.senha.validator()
            .nonEmpty()
            .minLength(6)
            .check()
        if ( !senha )
            resultadoAutenticacao.senhaInvalido = true

        return resultadoAutenticacao
    }

    suspend fun cadastrarUsuario( usuario: Usuario ) : Boolean {
        return try {
            iAutenticacaoRepository.cadastrarUsuario(usuario)
           // true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun logarUsuario(usuario: Usuario): Boolean {
        return try {
            iAutenticacaoRepository.logarUsuario(usuario)
           // true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun usuarioEstaLogado(): Boolean{
        return try {
            iAutenticacaoRepository.usuarioEstaLogado()
            //true
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            false
        }
    }
}