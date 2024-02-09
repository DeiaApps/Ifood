package com.andreaaf.loja.domain.usecase

data class ResultadoAutenticacao(//padrão será inválido e no autenticação verifica
    var nomeInvalido: Boolean = false, //true - mensagem
    var emailInvalido: Boolean = false,
    var senhaInvalido: Boolean = false,
    var telefoneInvalido: Boolean = false
){

    val sucessoCadastro: Boolean //sucesso é true
    get() = !(nomeInvalido || emailInvalido || senhaInvalido || telefoneInvalido)

    val sucessoLogin: Boolean //sucesso é true
        get() = !( emailInvalido || senhaInvalido )
}

//nomeInvalido || emailInvalido || senhaInvalido || telefoneInvalido
//false         || false          || false          || false  == true