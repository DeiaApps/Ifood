package com.andreaaf.appifood.domain.model

data class Loja(
    val nome: String,
    val razaoSocial: String,
    val cnpj: String,
    val categoria: Int,
    val especialidade: String,
    val imagemPerfil: String,
    val imagemCapa: String,
)
