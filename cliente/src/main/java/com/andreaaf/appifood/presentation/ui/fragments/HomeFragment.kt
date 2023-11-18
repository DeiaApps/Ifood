package com.andreaaf.appifood.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.FragmentHomeBinding
import com.andreaaf.appifood.domain.model.Categoria
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.appifood.helper.TipoLayout
import com.andreaaf.appifood.helper.exibirMensagem
import com.andreaaf.appifood.presentation.ui.adapters.CategoriasAdapter
import com.andreaaf.appifood.presentation.ui.adapters.LojasAdapter
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var ultimasLojasAdapter: LojasAdapter
    private lateinit var lojasAdapter: LojasAdapter
    private lateinit var categoriasAdapter: CategoriasAdapter

    private val listaCategorias = listOf(
        Categoria(
            "Promoções",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Promocoes_70tx.png?imwidth=128"
        ),
        Categoria(
            "Express",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Expressgrid1_8jHT.png?imwidth=128"
        ),
        Categoria(
            "Gourmet",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/gourmet_2w9x.png?imwidth=128"
        ),
        Categoria(
            "Pra Agora",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Praagora_v6nV.png?imwidth=128"
        ),
        Categoria(
            "Mercado",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Mercados_t4d6.png?imwidth=128"
        ),
        Categoria(
            "Carnes",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Carnes_fEiQ.png?imwidth=128"
        ),
        Categoria(
            "Bebidas",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Bebidas_b8Zu.png?imwidth=128"
        ),
        Categoria(
            "Farmácia",
            "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Farmaucia_dpUs.png?imwidth=128"
        )
    )


    private val listaLojas = listOf(
        Loja(
            "MC Donalds",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/fd746c33-65dd-42e6-8a23-f1551a3e1c13_MCDON_ISSHI.jpg?imwidth=128",
            "Lanches"
        ),
        Loja(
            "Outback",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/c8e3b624-92b8-492f-b0dd-3626f0b0203e/202009071233_cy5Z_i.png?imwidth=128",
            "Lanches"
        ),
        Loja(
            "Sodiê Doces",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/3527d5e0-6e0d-4c82-8739-59be42db2cfe/202211231534_ciQV_i.jpg?imwidth=128",
            "Doces & Bolos"
        ),
        Loja(
            "Jun Japanese Food",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/65956fa1-8c2a-4f9d-bd04-80cc3df3529a/202104031930_CcSl_i.png?imwidth=128",
            "Japonesa"
        ),
        Loja(
            "Mexicaníssimo Restaurante",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/cc006d82-8e09-4c16-a6b2-8ff36c5c2059/201909242315_n2HE_i.png?imwidth=128",
            "Mexicana"
        ),
        Loja(
            "We Coffee",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/5ffb4f8a-336b-4a3b-bb0a-638e7e8a5e62/202206131027_gq33_i.jpg?imwidth=128",
            "Cafeteria"
        ),
        Loja(
            "Habib´s",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/122de1c6-b543-4005-a4f4-e85decc392d7/202103031141_MDgU_i.jpg?imwidth=128g",
            "Lanches"
        ),
        Loja(
            "Black Dog",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/35ea9e06-55a4-471c-a498-e97cf6c02cab/202302240850_XFFE_i.jpg?imwidth=128",
            "Lanches"
        ),
        Loja(
            "La Guapa Empanadas",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/00f99118-dc58-4154-af81-832151217b27/202112281612_OeDH_i.png?imwidth=128",
            "Lanches"
        ),
        Loja(
            "Burger King",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/e525580e-8b59-4f72-926c-bbd58219439c/202308261835_2BUB.png?imwidth=128",
            "Lanches"
        ),
        Loja(
            "O Mineiro Prime",
            "https://static.ifood-static.com.br/image/upload/t_medium/logosgde/d3dc9849-26b3-4bcd-8087-b5b5f65f943d/202003302129_3SfR_i.jpg?imwidth=128",
            "Brasileira"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        )

        inicializarRecyclerViewUltimasLojas()
        inicializarRecyclerViewLojas()
        inicializarRecyclerFiltrosCategorias()
        inicializarSliderPromocional()
        inicializarFiltrosLojas()
        inicializarAvisoNotificacoes()

        return binding.root
        /*// Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)*/
    }

    private fun inicializarAvisoNotificacoes() {
        with( binding ){
            val menuItem = tbHome.menu.findItem(R.id.item_notificacao)
            val textAvisoNotificacao = menuItem.actionView?.findViewById(R.id.textAvisoNotificacao) as TextView
            textAvisoNotificacao.text= "5"
            //textAvisoNotificacao.visibility = View.GONE
        }
    }

    private fun inicializarFiltrosLojas() {

        val filtroRetirar = binding.chipRetirar.isChecked
        val filtroEntregaGratis = binding.chipEntregaGratis.isChecked

        binding.chipOrdenacao.setOnClickListener {

            val listaOrdenacao = arrayOf("Ordem Padrão", "Crescente", "Decrescente")
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Escolha uma ordenação")
                .setItems(listaOrdenacao) { _, posicao ->
                    val itemSelecionado = listaOrdenacao[posicao]
                    if (posicao == 0) {
                        binding.chipOrdenacao.text = "Ordenação"
                    } else {
                        binding.chipOrdenacao.text = itemSelecionado
                    }
                }
                .show()
        }
    }

    private fun inicializarSliderPromocional() {
        val listaSlides = mutableListOf<SlideModel>()

        val tipoEscala = ScaleTypes.CENTER_CROP
        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/1306PromotionsCampanhasSeloPIJCampanhasAlwaysOnjantarcomentregagratisprincipal_GYt4.png?imwidth=3840",
                scaleType = tipoEscala
            )
        )

        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/1306PromotionsCampanhasSeloPIJCampanhasAlwaysOnrestaurantesegprincipal_icmH.png?imwidth=1920",
                scaleType = tipoEscala
            )
        )

        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/CapaPrincipalGenericoPedeiFoodJaRoxo_kkVW.png?imwidth=3840",
                scaleType = tipoEscala
            )
        )

        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/0407precodebalcao01principal_hxyb.png?imwidth=1920",
                scaleType = tipoEscala
            )
        )
        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/1506famososnoifoodprincipal_Qdzl.png?imwidth=1920",
                scaleType = tipoEscala
            )
        )
        listaSlides.add(
            SlideModel(
                "https://static.ifood-static.com.br/image/upload/t_high/discoveries/0201restaurantesqueridinhos4principal_8IE6.png?imwidth=3840",
                scaleType = tipoEscala
            )
        )
        binding.sliderPromocional.setImageList(listaSlides)

        //Evento clique
        binding.sliderPromocional.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                activity?.exibirMensagem("Item clicado ($position)")
            }
        })
        /*binding.sliderPromocional.setSlideAnimation(
           AnimationTypes.ZOOM_IN)*/
    }

    private fun inicializarRecyclerFiltrosCategorias() {
        categoriasAdapter = CategoriasAdapter()
        categoriasAdapter.atualizarLista(listaCategorias)

        binding.rvFiltros.adapter = categoriasAdapter
        binding.rvFiltros.layoutManager = GridLayoutManager(
            context, 4
        )
    }

    private fun inicializarRecyclerViewLojas() {
        lojasAdapter = LojasAdapter(TipoLayout.VERTICAL) {
            findNavController().navigate(R.id.lojaFragment)
        }
        lojasAdapter.adicionarLista(listaLojas)
        binding.rvLojas.adapter = lojasAdapter
        binding.rvLojas.layoutManager = LinearLayoutManager(context)
    }

    private fun inicializarRecyclerViewUltimasLojas() {
        ultimasLojasAdapter = LojasAdapter(TipoLayout.HORIZONTAL) {
            findNavController().navigate(R.id.lojaFragment)
        }
        ultimasLojasAdapter.adicionarLista(listaLojas)
        binding.rvUltimasLojas.adapter = ultimasLojasAdapter
        binding.rvUltimasLojas.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
    }
}
