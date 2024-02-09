package com.andreaaf.loja.presentation.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreaaf.loja.domain.model.Usuario
import com.andreaaf.loja.domain.usecase.ResultadoAutenticacao
import com.andreaaf.loja.domain.usecase.AutenticacaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutenticacaoViewModel @Inject constructor(
    //Repository(MVVM + clean) ou UseCase(lógica de negócio/pode ter 'n')
    private val autenticacaoUseCase: AutenticacaoUseCase
): ViewModel() {

    private val _carregando = MutableLiveData<Boolean>()
    val carregando: LiveData<Boolean>
        get() = _carregando

    //não fica exposta    //private val resultadoValidacao = MutableLiveData<String>()
    private val _resultadoValidacao = MutableLiveData<ResultadoAutenticacao>()
    //Exposto: val validacao: MutableLiveData<String>
    val validacao: LiveData<ResultadoAutenticacao>
        get() = _resultadoValidacao //não pode alterar

    private val _sucesso = MutableLiveData<Boolean>()
    val sucesso: LiveData<Boolean>
        get() = _sucesso

    private val _sucessoUsuarioEstaLogado = MutableLiveData<Boolean>()
    val sucessoUsuarioEstaLogado: LiveData<Boolean>
        get() = _sucessoUsuarioEstaLogado

    fun usuarioEstaLogado() {
        _carregando.value = true
        viewModelScope.launch {
            //val retorno = true
            val retorno = autenticacaoUseCase.usuarioEstaLogado()
            _carregando.postValue(false)
            //println("retorno esta logado: $retorno")
            _sucessoUsuarioEstaLogado.postValue( retorno )
        }
    }

    fun logarUsuario(usuario: Usuario) {
        val resultadoAutenticacao = autenticacaoUseCase.validarLoginUsuario(usuario)
        _resultadoValidacao.value = resultadoAutenticacao

        if (resultadoAutenticacao.sucessoLogin) {
            _carregando.value = true
            viewModelScope.launch {
                //println("retorno usuário: ${usuario.email} - ${usuario.senha}")
                val retorno = autenticacaoUseCase.logarUsuario(usuario)
                //delay(13000)
                //println("retorno logar: $retorno")
                //_carregando.postValue(false) = só se for carregar em outro lugar
                _carregando.value = false//encerra carregamento
                _sucesso.postValue(retorno)
            }
        }
    }

    fun cadastrarUsuario(usuario: Usuario) {

        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)
        _resultadoValidacao.value = resultadoAutenticacao

        if (resultadoAutenticacao.sucessoCadastro) {
            _carregando.value = true
            viewModelScope.launch{
                val retorno = autenticacaoUseCase.cadastrarUsuario(usuario)
                Log.i("cadastro usuario firebase", "res: $retorno")
                _carregando.value = false
                _sucesso.postValue( retorno )
            }
        }
    }
}

