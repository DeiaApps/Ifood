package com.andreaaf.appifood.presentation.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.FragmentLojaBinding
import com.andreaaf.appifood.domain.model.Produto
import com.andreaaf.appifood.helper.TipoLayout
import com.andreaaf.appifood.presentation.ui.adapters.ProdutosAdapter

class LojaFragment : Fragment() {

    private val listaProdutos = listOf(
        Produto(
            "CHOPP BRAHMA OUTBACK 1L COM 25% DE DESCONTO",
            "O Chopp Brahma Outback com o sabor....",
            "R$ 22,40",
            "R$ 20,40",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/5221af98-5ad4-42e2-a767-23d1545b82d5/202011181213_E09M_.jpeg"
        ),
        Produto(
            "JR RIBS + WINGS JOEY + ICED TEA 500ML",
            "Um combo para vocÃª aproveitar...",
            "R$ 104,90",
            "R$ 99,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105101738_140L_.jpeg"
        ),
        Produto(
            "RIBS ON THE BARBIE + 2 ACOMPANHAMENTOS + ICED TEA 1L",
            "Nossa costela suÃ­na preparada em chama aberta como manda a tradiÃ§Ã£o australiana, vem com as saborosas cinnamon apples. Inclui 2 acompanhamentos Ã  sua escolha e um Iced Tea de 1l.",
            "R$ 124,90",
            "R$ 99,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/5221af98-5ad4-42e2-a767-23d1545b82d5/201911191742_qCKt_r.jpg"
        ),
        Produto(
            "THE OUTBACKER + COCA-COLA",
            "200g de hambÃºrguer de carne, queijo, picles, tomate, alface, cebola e maionese. Se preferir, peÃ§a com bacon tambÃ©m. ",
            "R$ 49,90",
            "",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202303090917_HK7C_i.jpg"
        )

    )

    private lateinit var binding: FragmentLojaBinding
    private lateinit var produtosDestaqueAdapter: ProdutosAdapter
    private lateinit var produtosAdapter: ProdutosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLojaBinding.inflate(
            inflater, container, false
        )

        inicializarToolbar()

        inicializarRecyclerViewProdutosDestaque()
        inicializarRecyclerViewProdutos()

        return binding.root

    }

    private fun inicializarToolbar() {
        with(binding){
            collapsedToolbar

            btnNavLojaVoltar.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }

            val appCompatActivity = (activity as AppCompatActivity)

            appCompatActivity.setSupportActionBar( collapsedToolbar )

            appCompatActivity.addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                        menuInflater.inflate(R.menu.loja_pesquisa, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return true
                    }

                },
                viewLifecycleOwner
            )

            //Configuração AppBar
            /*appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                println("AppBarChange : $verticalOffset")
                //0 - 357
                val navegacaoVertical = abs(verticalOffset)
                if( navegacaoVertical >= appBarLayout.totalScrollRange ){//Colapsado/fechado
                    textNavLojaTitulo.text = "Outback"
                }else if( navegacaoVertical == 0 ){//Expandido
                    textNavLojaTitulo.text = ""
                }else{//Scrool está acontecendo

                }
            }*/

        }
        /*val toolbar = binding.collapsedToolbar
        val navControler = findNavController()*/

        /*
        comportamento padrão, 2 exemplos
        navControler.currentDestination?.label = "" //ex.: 2
        toolbar.setupWithNavController(navControler)
        ex.:1 NavigationUI.setupWithNavController(toolbar, navControler)*/

    }

    private fun inicializarRecyclerViewProdutos() {

        produtosAdapter = ProdutosAdapter( TipoLayout.VERTICAL ){
            findNavController().navigate(R.id.produtoFragment)
        }
        produtosAdapter.adicionarLista( listaProdutos )
        binding.rvProdutos.adapter = produtosAdapter
        binding.rvProdutos.layoutManager = LinearLayoutManager(context)

    }

    private fun inicializarRecyclerViewProdutosDestaque() {

        produtosDestaqueAdapter = ProdutosAdapter( TipoLayout.HORIZONTAL ){
            findNavController().navigate(R.id.produtoFragment)
        }
        produtosDestaqueAdapter.adicionarLista( listaProdutos )
        binding.rvProdutosDestaque.adapter = produtosDestaqueAdapter
        binding.rvProdutosDestaque.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )

    }

}