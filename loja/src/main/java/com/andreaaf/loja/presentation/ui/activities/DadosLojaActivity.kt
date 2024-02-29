package com.andreaaf.loja.presentation.ui.activities

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.core.InternetConnectivity
import com.andreaaf.core.InternetConnectivityObserver
import com.andreaaf.core.esconderTeclado
import com.andreaaf.core.exibirMensagem
import com.andreaaf.loja.broadcasts.ConexaoWifiReceiver
import com.andreaaf.loja.databinding.ActivityDadosLojaBinding
import com.andreaaf.loja.presentation.ui.viewmodel.LojaViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DadosLojaActivity : AppCompatActivity(), InternetConnectivity {

    private val binding by lazy {
        ActivityDadosLojaBinding.inflate(layoutInflater)
    }

    private val autenticao by lazy {
        FirebaseAuth.getInstance()
    }

    private val armazenamento by lazy {
        FirebaseStorage.getInstance()
    }

    private val lojaViewModel: LojaViewModel by viewModels()

    private var uriImagemSelecionada: Uri? = null
    private var temPermissaoGaleria = false
    private var temPermissaoGaleriaCompat = false
    private lateinit var conexaoWifiReceiver: ConexaoWifiReceiver

    private val abrirGaleria = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
       // exibirMensagem( "imagem: $uriImagemSelecionada")
        if (uri != null) {
            binding.imagePerfilLoja.setImageURI(uri)
            uriImagemSelecionada = uri
        } else {
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializar()
    }

    private fun inicializar() {
        inicializarInternetConnectivityObserver()
        solicitarPermissoes()
        inicializarViews()
        inicializarListeners()
        inicializarObservables()
       // inicializarBroadcast()
    }

    private fun inicializarInternetConnectivityObserver() {
        //tem que inicializar a parte
        InternetConnectivityObserver
            .instance(this)
            .setCallback(this)
            .build()
    }

    private fun solicitarPermissoes() {

        //Verificar permissÃµes que o usuário já¡ tem
        val permissoesNegadas = mutableListOf<String>()
        val permissoesNegadasCompat = mutableListOf<String>()

        // >= TIRAMISU read_media_images
        // < TIRAMISU read_external_storage

        temPermissaoGaleria = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED

        temPermissaoGaleriaCompat = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (!temPermissaoGaleria)
            permissoesNegadas.add(android.Manifest.permission.READ_MEDIA_IMAGES)

        if (!temPermissaoGaleriaCompat)
            permissoesNegadas.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        //Solicitar permissÃµes
        if (permissoesNegadas.isNotEmpty() || permissoesNegadasCompat.isNotEmpty()) {

            val gerenciadorPermissoes = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissoes: Map<String, Boolean> ->

                temPermissaoGaleria = permissoes[android.Manifest.permission.READ_MEDIA_IMAGES]
                    ?: temPermissaoGaleria

                temPermissaoGaleriaCompat = permissoes[android.Manifest.permission.READ_EXTERNAL_STORAGE]
                    ?: temPermissaoGaleriaCompat
            }
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
                gerenciadorPermissoes.launch(permissoesNegadas.toTypedArray())
            }else{
                gerenciadorPermissoes.launch(permissoesNegadas.toTypedArray())
            }
        }
    }

    private fun inicializarViews() {//ok
        inicializarToolbar()
        inicializarSpinner()
        inicializarConfiguracoesGaleria()
        //inicializarBroadcast()
    }

    private fun inicializarBroadcast() {
        //direto na activity
        val wifiService = getSystemService( WifiManager::class.java )
        if ( wifiService.isWifiEnabled ){
            //poderia ser snaclBar
            exibirMensagem( "Você está sem conexao wifi :(")
            Log.i("broadcast_android", "Você está sem conexao wifi :(")
        }else {
            Log.i("broadcast_android", "Você está conectado :)")
        }
        //chama a classe
        conexaoWifiReceiver = ConexaoWifiReceiver()
        //com Broadcast
        IntentFilter().apply {
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        }.also {intentFilter ->
            //configura broadcast
            registerReceiver( conexaoWifiReceiver, intentFilter)
        }
    }

    private fun inicializarObservables() {
        lojaViewModel.validacao.observe(this) { sucesso -> //true
            if (!sucesso) {
                exibirMensagem("Preencha todos os campos para prosseguir")
            }
        }

        lojaViewModel.sucesso.observe(this) { sucessoCadastro ->
            if (sucessoCadastro) {
                navegarParaTelaInicial()
                exibirMensagem("Sucesso ao cadastrar Loja")
            } else {
                exibirMensagem("Erro ao cadastrar Loja")
            }
        }
    }

    fun navegarParaTelaInicial() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

    private fun inicializarListeners() {
        with(binding) {
            btnCadastrarLoja.setOnClickListener { view ->

                //Esconder o teclado
                view.esconderTeclado()

                //Desativar o focus
                editNomeLoja.clearFocus()
                editRazaoSocial.clearFocus()
                editCnpj.clearFocus()
                editTelefoneContatoLoja.clearFocus()
                editEspecialidade.clearFocus()
                //exibirMensagem("imagem: $uriImagemSelecionada")
                if (uriImagemSelecionada != null) {
                    lojaViewModel.cadastrarLoja(
                        Loja(
                            nome = editNomeLoja.text.toString(),
                            razaoSocial = editRazaoSocial.text.toString(),
                            cnpj = editCnpj.text.toString(),
                            categoria = 1,//configurar spinner depois
                            especialidade = editEspecialidade.text.toString(),
                            imagemPerfil = "https://www.google.com/",  // "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxpGO4QNGu1swn2ju5FEum-0-Hcak1iDLqMfQjFBe3l7gIX8zPaKIBTve1JlJH3JR-AqE&usqp=CAU",
                            imagemCapa = "https://www.google.com/"
                            //"https://tekimobile.com/wp-content/uploads/2019/04/capa-para-facebook-gatinho.jpg"
                        ), uriImagemSelecionada!!
                    )
                }else{
                    exibirMensagem("Selecione uma imagem de perfil primeiro")
                }
            }
        }
    }

    private fun inicializarConfiguracoesGaleria() {
        binding.btnSelecionarImagePerfil.setOnClickListener {
            if (temPermissaoGaleria || temPermissaoGaleriaCompat) {
                abrirGaleria.launch("image/*")//Mime Type
            } else {
                exibirMensagem("Você não tem permissão de galeria")
                // Toast.makeText(this, "Você não tem permissão de galeria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // falta a lista de permissões / solicitar

    private fun inicializarSpinner() {
        val categorias = listOf(
            "Selecione uma categoria",
            "Lanches", "Pizzas", "Japonesa", "Brasileira"
        )

        binding.spinnerCategorias.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )
    }

    private fun inicializarToolbar() {

        val toolbar = binding.includeToolbar.materialToolbarPrincipal
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Dados da Loja"
            setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onDestroy() {
        unregisterReceiver( conexaoWifiReceiver )
        super.onDestroy()
    }

    override fun onConnected() {
        Snackbar.make(binding.root, "Conectado", Snackbar.LENGTH_SHORT )
            .setAction("Close"){
                fun onClick(view: View?){}
            }
           // .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()

       // Toast.makeText(this, "Conectado à Internet", Toast.LENGTH_SHORT).show()
    }

    override fun onDisconnected() {
        Toast.makeText(this, "Sem conexão com Internet", Toast.LENGTH_SHORT).show()
    }
}