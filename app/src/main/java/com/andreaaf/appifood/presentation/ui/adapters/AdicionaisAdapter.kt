package com.andreaaf.appifood.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreaaf.appifood.databinding.ItemRvAdicionaisBinding
import com.andreaaf.appifood.domain.model.Adicional
import com.squareup.picasso.Picasso

class AdicionaisAdapter : Adapter<AdicionaisAdapter.AdicionaisViewHolder>() {

    private var listaAdicionais = emptyList<Adicional>()
    fun atualizarLista(lista: List<Adicional>) {
        listaAdicionais = lista
        notifyDataSetChanged()
    }

    inner class AdicionaisViewHolder(
        private val binding: ItemRvAdicionaisBinding
    ) : ViewHolder(binding.root) {

        fun bind(adicional: Adicional) {
            binding.textAdicionalTitulo.text = adicional.titulo
            binding.textAdicionalDescricao.text = adicional.descricao
            if (adicional.urlImagem.isNotEmpty()) {
                Picasso.get()
                    .load(adicional.urlImagem)
                    .into(binding.imageAdicional)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdicionaisViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemViewAdicionais = ItemRvAdicionaisBinding.inflate(
            inflater, parent, false
        )
        return AdicionaisViewHolder(itemViewAdicionais)

    }

    override fun onBindViewHolder(holder: AdicionaisViewHolder, position: Int) {
        val adicional = listaAdicionais[position]
        holder.bind(adicional)
    }

    override fun getItemCount(): Int {
        return listaAdicionais.size
    }

}