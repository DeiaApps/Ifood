package com.andreaaf.appifood.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreaaf.appifood.databinding.ActivityNavegacaoAbasBinding
import com.andreaaf.appifood.presentation.ui.fragments.BuscaFragment
import com.andreaaf.appifood.presentation.ui.fragments.HomeFragment
import com.andreaaf.appifood.presentation.ui.fragments.PedidosFragment
import com.andreaaf.appifood.presentation.ui.fragments.PerfilFragment
import com.google.android.material.tabs.TabLayoutMediator

class NavegacaoAbasActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityNavegacaoAbasBinding.inflate( layoutInflater )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        inicializaNavegacaoAbas()
    }


    private fun inicializaNavegacaoAbas() {
        val tabLayout = binding.tabLayoutNavegacao
        val viewPager = binding.viewPagerNavegacao
        /*
        Início -> HomeFragment()
        Busca -> BuscaFragment()
        * */
        /*val listaAbas = listOf(
            MinhaAba("Início", HomeFragment()),
            MinhaAba("Busca", BuscaFragment()),
            MinhaAba("Pedidos", PedidosFragment()),
            MinhaAba("Perfil", PerfilFragment()),
        )*/

        val listaAbas = listOf(
            AbaCustomizada("Inicio", HomeFragment()),
            AbaCustomizada( "Busca", BuscaFragment()),
            AbaCustomizada( "Pedidos", PedidosFragment()),
            AbaCustomizada( "Perfil", PerfilFragment())
            /*"Iníco",
            "Busca",
            "Pedidos"*/,
            /*"Perfil1",
            "Perfil2",
            "Perfil3",*/
        )
        viewPager.adapter = ViewPagerAdapter(
            listaAbas, supportFragmentManager, lifecycle
        )
        TabLayoutMediator(tabLayout, viewPager){ aba, posicao ->
            val abaCustomizada = listaAbas[posicao]
            aba.text = abaCustomizada.titulo
            //aba.text = listaAbas[posicao]
        }.attach()
    }
}
data class AbaCustomizada(
    val titulo: String,
    val fragment: Fragment
)

class ViewPagerAdapter(
    //private val listaAbas: List<MinhaAba>,
    private val listaAbas: List<AbaCustomizada>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return listaAbas.size
    }

    override fun createFragment(position: Int): Fragment {
        /* Inico(0) -> HomeFragment*/
        /*val aba = listaAbas[position]
        return aba.fragment*/
        /*when( position ){ //cria cfme necessário
            1 -> return BuscaFragment()
            2 -> return PedidosFragment()*/
        val abaCustomizada = listaAbas[position]
           /* 3 -> PerfilFragment()
            4 -> PerfilFragment()
            5 -> PerfilFragment()*/
       // }
      //  return HomeFragment()
        return abaCustomizada.fragment
    }

}