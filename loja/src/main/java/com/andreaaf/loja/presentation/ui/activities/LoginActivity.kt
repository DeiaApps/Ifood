package com.andreaaf.loja.presentation.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.andreaaf.loja.domain.model.Usuario
import com.andreaaf.core.AlertaMensagem
import com.andreaaf.core.esconderTeclado
import com.andreaaf.core.exibirMensagem
import com.andreaaf.loja.databinding.ActivityLoginBinding
import com.andreaaf.loja.presentation.ui.viewmodel.AutenticacaoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by lazy{ActivityLoginBinding.inflate( layoutInflater ) }

    private val alertaMensagem by lazy { AlertaMensagem(this) }

    private val autenticacaoViewModel: AutenticacaoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //FirebaseAuth.getInstance().signOut()
        inicializar()
    }

    override fun onStart() {
        super.onStart()
        //TODO problema de usuário logado
        autenticacaoViewModel.usuarioEstaLogado()
    }



    private fun inicializar() {
        supportActionBar?.hide()

        //Verifica usuario logado
        autenticacaoViewModel.usuarioEstaLogado()

        inicializarEventoClique()
        inicializarObservables()
    }
    private fun inicializarObservables() {

        autenticacaoViewModel.validacao.observe(this){ rAutenticacao->
            with(binding){
                editLoginEmail.error =
                    if (rAutenticacao.emailInvalido) "Preencha um e-mail válido" else null

                editLoginSenha.error =
                    if (rAutenticacao.senhaInvalido) "Senha errada" else null
            }
        }

        autenticacaoViewModel.carregando.observe(this){carregando ->
            if (carregando){
                alertaMensagem?.exibir("Efetuando login de usuário!")
            }else{
                alertaMensagem?.fechar()
            }
        }
        autenticacaoViewModel
            .sucessoUsuarioEstaLogado
            .observe(this){ sucessoUsuarioEstaLogado ->
                if( sucessoUsuarioEstaLogado ){
                    navegarParaTelaPrincipal() }
             /*       exibirMensagem("SUCESSO LOGIN")
                }else {
                    exibirMensagem("ERRO LOGIN")
                }   NÃO PRECISA DAS MSG POIS QDO LOGA VAI APRA OUTRA TELA*/
            }

        autenticacaoViewModel.sucesso.observe(this){ sucessoLogin->
            if (sucessoLogin){
                navegarParaTelaPrincipal()
                exibirMensagem("Sucesso ao logar usuário")
            }else{
                limparCampos()
                exibirMensagem("E-mail e senha inválido, preencha novamente!")
            }
        }
    }

    private fun limparCampos() {
        binding.editLoginSenha.setText("")
        binding.editLoginEmail.setText("")
    }

    private fun navegarParaTelaPrincipal() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

    private fun inicializarEventoClique() {
        with(binding){
            textLoginCadastro.setOnClickListener {
                startActivity(
                    Intent(applicationContext, CadastroActivity::class.java)
                )
            }

            btnLoginEntrar.setOnClickListener {view->
                //Esconder o teclado
                view.esconderTeclado()

                //Desativar focus
                editLoginEmail.clearFocus()
                editLoginSenha.clearFocus()

                autenticacaoViewModel.logarUsuario(
                    Usuario(
                        email = editLoginEmail.text.toString(),
                        senha = editLoginSenha.text.toString()
                    )
                )

            }
        }
    }
}