package com.andreaaf.appifood.presentation.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.ActivityCadastroBinding
import com.andreaaf.appifood.domain.model.Usuario
import com.andreaaf.appifood.helper.AlertaMensagem
import com.andreaaf.appifood.helper.esconderTeclado
import com.andreaaf.appifood.helper.exibirMensagem
import com.andreaaf.appifood.presentation.viewmodel.AutenticacaoViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
Pacotes comuns: utils, commons, base, helper
Objetivo da aula:
+ Esconder teclado (extensão) ok
+ Utilizando focus (extensão) ok
	- clearFocus
	- requestFocus
+ Cadastrar Usuário Firebase
+ Injeção de Interface (repository)
+ Validar login
+ Login usuÃ¡rio firebase
* */


//esta usando inject precisa
@AndroidEntryPoint
class CadastroActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityCadastroBinding.inflate( layoutInflater )
    }

    private val alertaMensagem by lazy { AlertaMensagem(this) }

    private val autenticacaoViewModel: AutenticacaoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        //inits- todas as interações
        inicializar()
    }

    private fun inicializar() {//para organizar
        inicializarViews()
        inicializarListeners()
        inicializarObservables()
    }

    private fun inicializarViews() {
        inicializarToolbar()
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.materialToolbarPrincipal
        setSupportActionBar( toolbar )

        supportActionBar?.apply {
            setTitle("Cadastro de usuário")
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun inicializarListeners() {
        with( binding ){
            //editCadastroNome.error = "Preencha nome"
            btnCadastrar.setOnClickListener {view->

                //Esconder o teclado
                view.esconderTeclado()

                //Desativar focus
                editCadastroNome.clearFocus()
                editCadastroEmail.clearFocus()
                editCadastroSenha.clearFocus()
                editCadastroTelefone.clearFocus()

                //editCadastroSenha.requestFocus()//foco no item específico
                //editCadastroSenha.esconderTeclado()//pode usar em qq view

               // cadastroViewModel.validarUsuario(
                autenticacaoViewModel.cadastrarUsuario(
                    Usuario(//parâmetro nomeado
                        nome = editCadastroNome.text.toString(),
                        email = editCadastroEmail.text.toString(),
                        senha = editCadastroSenha.text.toString(),
                        telefone = editCadastroTelefone.text.toString()
                    )
                )
            }
        }
    }
    /*Tem 2 estratégias -> tanto faz qual usar
    nomeInvalido = true
        if( nomeInvalido )
            executa algo

     nomeInvalido = true
        if( !nomeInvalido )
            executa algo
    */
    private fun inicializarObservables() {
        autenticacaoViewModel.validacao.observe(this){
            with( binding ){

                editCadastroNome.error =
                    if ( it.nomeInvalido ) getString(R.string.erro_nome_cadastro) else null

                editCadastroEmail.error =
                    if ( it.emailInvalido ) getString(R.string.erro_email_cadastro) else null

                editCadastroSenha.error =
                    if ( it.senhaInvalido ) getString(R.string.erro_senha_cadastro) else null

                editCadastroTelefone.error =
                    if ( it.telefoneInvalido ) getString(R.string.erro_telefone_cadastro) else null

            }
           //EXIBE FOCO = binding.editCadastroNome.exibirTecladoEFoco()
        }

        autenticacaoViewModel.carregando.observe(this){ carregando ->
            if ( carregando ){
                alertaMensagem?.exibir("Cadastrando usuário")
            }else{
                alertaMensagem?.fechar()
            }
        }

        autenticacaoViewModel.sucesso.observe(this){ sucessoCadastro ->
            if( sucessoCadastro ){
                navegarParaLogin()
                exibirMensagem("Sucesso ao cadastrar usuário")
            }else{
                exibirMensagem("Erro ao cadastrar usuário")
            }
        }
    }

    fun navegarParaLogin(){
        startActivity(
            Intent( this, LoginActivity::class.java)
        )
        finish()
    }
}