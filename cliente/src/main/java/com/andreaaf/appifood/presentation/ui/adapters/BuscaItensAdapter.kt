package com.andreaaf.appifood.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreaaf.appifood.databinding.ItemRvBuscaLojaProdutosBinding
import com.andreaaf.appifood.databinding.ItemRvCategoriasBinding
import com.andreaaf.appifood.domain.model.Categoria
import com.andreaaf.appifood.domain.model.Loja
import com.squareup.picasso.Picasso

class BuscaItensAdapter : RecyclerView.Adapter<BuscaItensAdapter.BuscaItensViewHolder>() {

    private var listaLojas = emptyList<Loja>()
    fun atualizarLista(lista: List<Loja>){
        listaLojas = lista
        notifyDataSetChanged()
    }

    inner class BuscaItensViewHolder(
        private val binding: ItemRvBuscaLojaProdutosBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(loja: Loja){
            with(binding) {
                includeLoja.textLojaNome.text = loja.nome
                if (loja.foto.isNotEmpty()) {
                    Picasso.get()
                        .load(loja.foto)
                        .into(includeLoja.imageLojaPerfil)
                }
                //configurar Recyclerview
                rvBuscaProdutos
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuscaItensAdapter.BuscaItensViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemViewBuscaLojaProdutos = ItemRvBuscaLojaProdutosBinding.inflate(
            inflater, parent, false
        )
        return BuscaItensViewHolder(itemViewBuscaLojaProdutos)
    }

    override fun getItemCount(): Int {
        return listaLojas.size
    }

    override fun onBindViewHolder(holder: BuscaItensAdapter.BuscaItensViewHolder, position: Int) {
        val loja = listaLojas[position]
        holder.bind( loja )
    }
}