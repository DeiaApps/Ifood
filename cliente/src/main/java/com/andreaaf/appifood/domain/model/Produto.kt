package com.andreaaf.appifood.domain.model

data class Produto(
    val titulo: String,
    val descricao: String,
    val preco: String,
    val precoDesconto: String,
    val urlImagem: String
)
