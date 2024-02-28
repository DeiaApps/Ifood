package com.andreaaf.appifood.domain.model

data class Loja(
    /*val nome: String,
    val foto: String,
    val categoria: String*/
    val nome: String,
    val razaoSocial: String,
    val cnpj: String,
    val categoria: Int,
    val especialidade: String,
    val imagemPerfil: String,  // VOLTANDO NULA NO DEBUG
    val imagemCapa: String,
)
