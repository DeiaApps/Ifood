package com.andreaaf.core

sealed class UIStatus{
    class Sucesso(var status: Boolean, var lista: List<String>?) : UIStatus()
    class Erro(var status: Boolean) : UIStatus()
}
