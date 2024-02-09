package com.andreaaf.loja.domain.model

data class Usuario(
    val nome: String = "",
    val email: String,
    val senha: String,
    val telefone: String= ""
)
