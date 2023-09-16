package com.andreaaf.appifood.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreaaf.appifood.databinding.ItemRvCategoriasBinding
import com.andreaaf.appifood.domain.model.Categoria
import com.andreaaf.appifood.domain.model.Loja
import com.squareup.picasso.Picasso

class CategoriasAdapter : Adapter<CategoriasAdapter.CategoriaViewHolder>() {

    private var listaCategorias = emptyList<Categoria>()
    fun atualizarLista( lista: List<Categoria> ){
        listaCategorias = lista
        notifyDataSetChanged()
    }

    inner class CategoriaViewHolder(
        private val binding: ItemRvCategoriasBinding
    ) : ViewHolder(binding.root){

        fun bind(categoria: Categoria){
            binding.textCategoriaNome.text = categoria.nome
            if (categoria.urlImagem.isNotEmpty()){
                Picasso.get()
                    .load( categoria.urlImagem )
                    .into( binding.imageCategoria )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemViewCategorias = ItemRvCategoriasBinding.inflate(
            inflater, parent, false
        )
        return CategoriaViewHolder( itemViewCategorias )
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.bind( categoria )
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }
}