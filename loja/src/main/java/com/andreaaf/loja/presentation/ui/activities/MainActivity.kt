package com.andreaaf.loja.presentation.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.andreaaf.loja.R
import com.andreaaf.loja.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate( layoutInflater )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root )

        inicializar()
    }

    private fun inicializar() {
        inicializarViews()
        //inicializarListeners()
        //inicializarObservables()
    }

    private fun inicializarViews() {
        inicializarToolbar()
        inicializarMenuPrincipal()
    }

    private fun inicializarMenuPrincipal() {

        addMenuProvider( object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_principal_loja, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when( menuItem.itemId ){
                    R.id.item_dados_loja -> {
                        startActivity(
                            Intent(applicationContext, DadosLojaActivity::class.java)
                        )
                    }
                    R.id.item_dados_loja ->{
                        startActivity(
                            Intent(applicationContext, DadosLojaActivity::class.java)
                        )
                    }
                    R.id.item_cardapio ->{
                        startActivity(
                            Intent(applicationContext, CardapioActivity::class.java)
                        )
                    }
                    R.id.item_taxa_tempo ->{
                        startActivity(
                            Intent(applicationContext, TaxaTempoActivity::class.java)
                        )
                    }
                    R.id.item_sair -> {
                        FirebaseAuth.getInstance().signOut()
                        startActivity(
                            Intent(applicationContext, LoginActivity::class.java)
                        )
                    }
                }

                return true

            }

        })

    }

    private fun inicializarToolbar() {

        val toolbar = binding.includeToolbar.materialToolbarPrincipal
        setSupportActionBar( toolbar )

        supportActionBar?.apply {
            title = "Gerenciamento LOJA"
        }

    }

}