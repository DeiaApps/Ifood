package com.andreaaf.appifood.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreaaf.appifood.R
import com.andreaaf.appifood.databinding.FragmentProdutoBinding
import com.andreaaf.appifood.domain.model.Adicional
import com.andreaaf.appifood.presentation.ui.adapters.AdicionaisAdapter

class ProdutoFragment : Fragment() {

    private lateinit var binding: FragmentProdutoBinding
    private lateinit var adicionaisAdapter: AdicionaisAdapter

    private val listaAdicionais = listOf(
        Adicional(
            "Manteiga Outback",
            "Lorem ipsum dolorem sit amet, ipsum dolorem sit amet",
            "R$ 5,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105140100_8Y4Q_.jpeg"
        ),
        Adicional(
            "Molho Barbecue",
            "Lorem ipsum dolorem sit amet, ipsum dolorem sit amet",
            "R$ 8,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105140018_2664_.jpeg"
        ),
        Adicional(
            "Manteiga Outback",
            "Lorem ipsum dolorem sit amet, ipsum dolorem sit amet",
            "R$ 5,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105140018_20W4_f.png"
        ),
        Adicional(
            "Thunder",
            "Lorem ipsum dolorem sit amet, ipsum dolorem sit amet",
            "R$ 5,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105140100_TV7Y_f.png"
        ),
        Adicional(
            "Manteiga Outback",
            "Lorem ipsum dolorem sit amet, ipsum dolorem sit amet",
            "R$ 5,90",
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/185e2a09-94cb-49af-88cb-4b0de2df6dc5/202105140100_8Y4Q_.jpeg"
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProdutoBinding.inflate(
            inflater, container, false
        )

        inicializarToolbar()
        inicializarRecyclerViewAdicionais()

        return binding.root

    }

    private fun inicializarToolbar() {

        with(binding){
            btnNavProdutoVoltar.setOnClickListener {
                findNavController().navigate(R.id.lojaFragment)
            }
        }

        //navControler.currentDestination?.label = ""
        //toolbar.setupWithNavController(navControler)
        //NavigationUI.setupWithNavController(toolbar, navControler)

    }

    private fun inicializarRecyclerViewAdicionais() {
        adicionaisAdapter = AdicionaisAdapter()
        adicionaisAdapter.atualizarLista(listaAdicionais)
        binding.rvAdicionais.adapter = adicionaisAdapter
        binding.rvAdicionais.layoutManager = LinearLayoutManager(context)
    }


}