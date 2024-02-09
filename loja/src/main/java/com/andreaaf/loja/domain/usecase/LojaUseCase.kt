package com.andreaaf.loja.domain.usecase

import android.net.Uri
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.loja.data.remote.firebase.repositoy.ILojaRepository
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import javax.inject.Inject

class LojaUseCase@Inject constructor(
    private val iLojaRepository: ILojaRepository
) {//CadastrarLojaUseCase, RecuperarDadosLojaUseCase

    fun validarDadosLojas( loja: Loja) : Boolean{

        val nome = loja.nome.validator()
            .nonEmpty()
            .minLength(3)
            .maxLength(32)
            .check()

        val razaoSocial = loja.razaoSocial.validator()
            .nonEmpty()
            .minLength(3)
            .maxLength(150)
            .check()

        val cnpj = loja.cnpj.validator()
            .nonEmpty()
            .check()

        val categoria = loja.categoria.toString().validator()
            .nonEmpty()
            .validNumber()
            .check()

        val especialidade = loja.especialidade.validator()
            .nonEmpty()
            .minLength(3)
            .maxLength(32)
            .check()

        val imagemPerfil = loja.imagemPerfil.validator()
            .nonEmpty()
            .validUrl()
            .check()

        val imagemCapa = loja.imagemCapa.validator()
            .nonEmpty()
            .validUrl()
            .check()

        if( nome )          return false
        if( razaoSocial )   return false
        if( cnpj )          return false
        if( categoria )     return false
        if( especialidade ) return false
        if( imagemPerfil )  return true
        if( imagemCapa )    return true

        return true

    }

    suspend fun cadastrarLoja( loja: Loja, uri: Uri ) : Boolean {
        return try {
            iLojaRepository.cadastrar( loja, uri )
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


}