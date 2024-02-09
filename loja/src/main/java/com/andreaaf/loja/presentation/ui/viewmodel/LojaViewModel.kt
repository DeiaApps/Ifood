package com.andreaaf.loja.presentation.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.loja.domain.usecase.LojaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LojaViewModel @Inject constructor(
    private val lojaUseCase: LojaUseCase
): ViewModel() {

    private val _resultadoValidacao = MutableLiveData<Boolean>()
    val validacao: LiveData<Boolean>
        get() = _resultadoValidacao

    private val _sucesso = MutableLiveData<Boolean>()
    val sucesso: LiveData<Boolean>
        get() = _sucesso

    fun cadastrarLoja( loja: Loja, uri: Uri){

        val resultado = lojaUseCase.validarDadosLojas( loja )
        _resultadoValidacao.value = resultado
        Log.i("cadastro_loja", "resultado: $resultado")
        if ( resultado ){
            viewModelScope.launch {
                val retorno = lojaUseCase.cadastrarLoja( loja, uri )
                _sucesso.postValue( retorno )
            }
        }

    }
}