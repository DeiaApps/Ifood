package com.andreaaf.loja.presentation.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.andreaaf.loja.domain.model.Usuario
import com.andreaaf.core.AlertaMensagem
import com.andreaaf.core.esconderTeclado
import com.andreaaf.core.exibirMensagem
import com.andreaaf.loja.R
import com.andreaaf.loja.databinding.ActivityCadastroBinding
import com.andreaaf.loja.presentation.ui.viewmodel.AutenticacaoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CadastroActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityCadastroBinding.inflate( layoutInflater )}

    private val alertaMensagem by lazy { AlertaMensagem(this) }

    private val autenticacaoViewModel: AutenticacaoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializar()
    }

    private fun inicializar() {
        inicializarToolbar()
        inicializarListeners()
        inicializarObservables()
    }

    private fun inicializarListeners() {
        with( binding ){
            btnCadastrar.setOnClickListener {view->
                //Esconder o teclado
                view.esconderTeclado()

                //Desativar focus
                editCadastroNome.clearFocus()
                editCadastroEmail.clearFocus()
                editCadastroSenha.clearFocus()
                editCadastroTelefone.clearFocus()

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

    private fun inicializarToolbar() {
        val toolbar = binding.includeToolbar.materialToolbarPrincipal
        setSupportActionBar( toolbar )

        supportActionBar?.apply {
            setTitle("Cadastro de usuário")
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun navegarParaLogin(){
        startActivity(
            Intent( this, LoginActivity::class.java)
        )
        finish()
    }
}