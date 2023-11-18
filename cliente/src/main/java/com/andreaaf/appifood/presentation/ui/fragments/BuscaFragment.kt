package com.andreaaf.appifood.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.andreaaf.appifood.databinding.FragmentBuscaBinding
import com.andreaaf.appifood.domain.model.Categoria
import com.andreaaf.appifood.presentation.ui.activities.AbaCustomizada
import com.andreaaf.appifood.presentation.ui.activities.ViewPagerAdapter
import com.andreaaf.appifood.presentation.ui.adapters.CategoriasAdapter
import com.google.android.material.tabs.TabLayoutMediator

class BuscaFragment : Fragment() {

    private lateinit var binding: FragmentBuscaBinding
    private lateinit var categoriasAdapter: CategoriasAdapter

    private val listaCategorias = listOf(
        Categoria("Promoções","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Promocoes_70tx.png?imwidth=128"),
        Categoria("Express","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Expressgrid1_8jHT.png?imwidth=128"),
        Categoria("Gourmet","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/gourmet_2w9x.png?imwidth=128"),
        Categoria("Pra Agora","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Praagora_v6nV.png?imwidth=128"),
        Categoria("Mercado", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Mercados_t4d6.png?imwidth=128"),
        Categoria("Carnes","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Carnes_fEiQ.png?imwidth=128"),
        Categoria("Bebidas","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Bebidas_b8Zu.png?imwidth=128"),
        Categoria("Farmácia","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Farmaucia_dpUs.png?imwidth=128")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscaBinding.inflate(
            inflater, container, false
        )
        inicializarRecyclerFiltrosCategorias()
        inicializarNavegacaoAbas()

        return binding.root
    }

    private fun inicializarNavegacaoAbas() {
        val tabLayout = binding.tabLayoutNavegacao
        val viewPager = binding.viewPagerNavegacao
        val listaAbas = listOf(
            AbaCustomizada("Loja", BuscaLojasFragment()),
            AbaCustomizada("Itens", BuscaItensFragment()),
        )

        val supportFragmentManager = activity?.supportFragmentManager
        if (supportFragmentManager != null) {

           viewPager.adapter = ViewPagerAdapter(
               listaAbas, supportFragmentManager, lifecycle
           )
            TabLayoutMediator(tabLayout, viewPager){ aba, posicao ->
                val abaCustomizada = listaAbas[posicao]
                aba.text = abaCustomizada.titulo
            }.attach()
        }
    }

    private fun inicializarRecyclerFiltrosCategorias() {
        categoriasAdapter = CategoriasAdapter()
        categoriasAdapter.atualizarLista(listaCategorias)

        binding.rvFiltrosBusca.adapter = categoriasAdapter
        binding.rvFiltrosBusca.layoutManager = GridLayoutManager(
            context, 4
        )
    }
}