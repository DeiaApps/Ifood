package com.andreaaf.core

sealed class UIStatus<out T>{
    class Sucesso<T>(var dados: T) : UIStatus<T>()
    class Erro(var mensagemErro: String) : UIStatus<Nothing>()
}
