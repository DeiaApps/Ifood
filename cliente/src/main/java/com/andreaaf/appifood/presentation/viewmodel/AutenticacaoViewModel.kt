package com.andreaaf.appifood.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreaaf.appifood.domain.model.Usuario
import com.andreaaf.appifood.domain.usecase.AutenticacaoUseCase
import com.andreaaf.appifood.domain.usecase.ResultadoAutenticacao
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

    /*  private val _navegarParaLogin = MutableLiveData<Boolean>()
    val navegarParaLogin: MutableLiveData<Boolean>
    get() = _navegarParaLogin*/

    /*  private val _estaLogado = MutableLiveData<Boolean>()
    val estaLogado: MutableLiveData<Boolean>
        get() = _estaLogado

    fun usuarioEstaLogado(){
        _carregando.value = true
        viewModelScope.launch {
            val retorno = true
            //val retorno = autenticacaoUseCase.usuarioEstaLogado()
            _carregando.postValue (false)
            //println("retorno esta logado: $retorno")
            _sucesso.postValue(retorno)
            //_estaLogado.postValue(retorno)
        }
    }*/

    private val _sucessoUsuarioEstaLogado = MutableLiveData<Boolean>()
    val sucessoUsuarioEstaLogado: LiveData<Boolean>
        get() = _sucessoUsuarioEstaLogado

    fun usuarioEstaLogado() {
        _carregando.value = true
        viewModelScope.launch {
            val retorno = true
            //val retorno = autenticacaoUseCase.usuarioEstaLogado()
            //delay(5000L)
            _carregando.postValue(false)
            //println("retorno esta logado: $retorno")
            _sucessoUsuarioEstaLogado.postValue(retorno)
        }
    }

    fun logarUsuario(usuario: Usuario) {
        //val resultadoAutenticacao = validarUsuario(usuario)
        //val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)
        val resultadoAutenticacao = autenticacaoUseCase.validarLoginUsuario(usuario)
        _resultadoValidacao.value = resultadoAutenticacao
        if (resultadoAutenticacao.sucessoLogin) {
            _carregando.value = true
            viewModelScope.launch {
                println("retorno usuário: ${usuario.email} - ${usuario.senha}")
                val retorno = autenticacaoUseCase.logarUsuario(usuario)
                //delay(13000)
                println("retorno logar: $retorno")
                // _carregando.value = false //encerra carregamento
               // _carregando.postValue(false) //encerra carregamento
                _carregando.value = false
                //  _sucesso.postValue(true)
                _sucesso.postValue(retorno)
            }
        }
    }
    /* fun validarUsuario(usuario: Usuario) : ResultadoAutenticacao {
            val resultadoAutenticacao = autenticacaoUseCase.validarUsuario(usuario)
            _resultadoValidacao.value = resultadoAutenticacao
            return resultadoAutenticacao
        }*/

    fun cadastrarUsuario(usuario: Usuario) {
        val resultadoAutenticacao = autenticacaoUseCase.validarCadastroUsuario(usuario)
        _resultadoValidacao.value = resultadoAutenticacao
        /*val resultadoAutenticacao = validarUsuario(usuario)
            _resultadoValidacao.value = resultadoAutenticacao*/
        if (resultadoAutenticacao.sucessoCadastro) {
            // se fosse livedata if (_resultadoValidacao.value!!.sucessoCadastro) {
            //inicializa o carregando
            _carregando.value = true
            viewModelScope.launch(Dispatchers.IO) {
                val retorno = autenticacaoUseCase.cadastrarUsuario(usuario)
                //delay(3000)
                // _carregando.value = false //encerra carregamento
                //_carregando.postValue(false) //encerra carregamento
                _carregando.value = false
                _sucesso.postValue(retorno)
                // _sucessoCadastro.postValue(true)
                // _sucessoCadastro.postValue(retorno)
                //_navegarParaLogin.postValue( retorno )
            }
        }
    }
}

/* virou cadastrarUsuario()
fun validarUsuario( usuario: Usuario ){//guarda o estado da autenticação
    val resultadoAutenticacao = autenticacaoUseCase.validarUsuario( usuario )
    _resultadoValidacao.value = resultadoAutenticacao}
*/