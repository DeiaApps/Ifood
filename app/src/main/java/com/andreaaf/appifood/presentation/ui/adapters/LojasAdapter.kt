package com.andreaaf.appifood.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreaaf.appifood.databinding.ItemRvLojasBinding
import com.andreaaf.appifood.databinding.ItemRvUltimasLojasBinding
import com.andreaaf.appifood.domain.model.Loja
import com.andreaaf.appifood.helper.Constantes
import com.andreaaf.appifood.helper.TipoLayout
import com.squareup.picasso.Picasso

class LojasAdapter(
    private val tipoLayout: TipoLayout,
    private val onClick: () -> Unit
) : Adapter<ViewHolder>() {

    private var listaLojas = emptyList<Loja>()
    fun adicionarLista( lista: List<Loja>){
        listaLojas = lista
        notifyDataSetChanged()
    }

    inner class UltimasLojasViewHolder(//ViewHolder
       private val binding: ItemRvUltimasLojasBinding
    ) : ViewHolder(binding.root){
        fun bind(loja: Loja){
            binding.textUltimaLojaNome.text = loja.nome
            binding.clItemUltimasLojas.setOnClickListener {
                onClick
            }
            if (loja.foto.isNotEmpty()){
                Picasso.get()
                    .load( loja.foto )
                    .into(binding.imageUltimaLojaPerfil)
            }
        }
    }

    inner class LojasViewHolder(//ViewHolder
        private val binding: ItemRvLojasBinding
    ) : ViewHolder(binding.root){
        fun bind(loja: Loja){
            binding.textLojaNome.text = loja.nome
            binding.clItemLojas.setOnClickListener {
                onClick()
            }

            binding.textLojaCategoria.text = loja.categoria
            if (loja.foto.isNotEmpty()){
                Picasso.get()
                    .load( loja.foto )
                    .into(binding.imageLojaPerfil)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (tipoLayout == TipoLayout.HORIZONTAL) {
            val inflater = LayoutInflater.from(parent.context)
            val itemViewUltimasLojas = ItemRvUltimasLojasBinding.inflate(
                inflater, parent, false
            )
            return UltimasLojasViewHolder(itemViewUltimasLojas)
        }
        val inflater = LayoutInflater.from(parent.context)
        val itemViewLojas = ItemRvLojasBinding.inflate(
            inflater, parent, false
        )
        return LojasViewHolder(itemViewLojas)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loja = listaLojas[position]
        //holder.bind(loja)
        when( holder ){
            is UltimasLojasViewHolder -> holder.bind(loja)
            is LojasViewHolder -> holder.bind(loja)
        }
    }

    override fun getItemCount(): Int {
        return listaLojas.size
    }
}