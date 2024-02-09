package com.andreaaf.appifood.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarNavegacao()
        /*        binding.bottomNavigationPrincipal.setOnClickListener { view ->
            view.findNavController()
        }*/
    }

    private fun inicializarNavegacao() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerPrincipal) as NavHostFragment
        val navController = navHostFragment.navController

        //val idFragment = navController.currentDestination?.id

        NavigationUI.setupWithNavController(
            binding.bottomNavigationPrincipal, navController
        )
    }
}