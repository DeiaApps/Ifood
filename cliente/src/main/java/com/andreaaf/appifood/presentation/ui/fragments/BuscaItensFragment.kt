package com.andreaaf.appifood.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.FragmentBuscaItensBinding
import com.andreaaf.appifood.databinding.FragmentBuscaLojasBinding
import com.andreaaf.appifood.databinding.FragmentLojaBinding
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.appifood.helper.TipoLayout
import com.andreaaf.appifood.presentation.ui.adapters.BuscaItensAdapter
import com.andreaaf.appifood.presentation.ui.adapters.LojasAdapter

class BuscaItensFragment : Fragment() {

    private lateinit var binding: FragmentBuscaItensBinding
    private lateinit var buscaItensAdapter: BuscaItensAdapter

    private val listaLojas = listOf(
        Loja("MC Donalds","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/fd746c33-65dd-42e6-8a23-f1551a3e1c13_MCDON_ISSHI.jpg?imwidth=128", "Lanches"),
        Loja("Outback","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/c8e3b624-92b8-492f-b0dd-3626f0b0203e/202009071233_cy5Z_i.png?imwidth=128", "Lanches"),
        Loja("Sodiê Doces","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/3527d5e0-6e0d-4c82-8739-59be42db2cfe/202211231534_ciQV_i.jpg?imwidth=128", "Doces & Bolos")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentBuscaItensBinding.inflate(
           inflater, container, false
       )
        inicializarRecyclerViewBuscaItens()

        return binding.root
    }

    private fun inicializarRecyclerViewBuscaItens() {
        buscaItensAdapter = BuscaItensAdapter()
        buscaItensAdapter.atualizarLista(listaLojas)
        binding.rvBuscaItens.adapter = buscaItensAdapter
        binding.rvBuscaItens.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false)
        }
    }

