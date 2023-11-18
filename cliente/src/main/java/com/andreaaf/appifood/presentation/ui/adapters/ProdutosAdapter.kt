package com.andreaaf.appifood.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreaaf.appifood.databinding.ItemRvProdutosBinding
import com.andreaaf.appifood.databinding.ItemRvProdutoDestaqueBinding
import com.andreaaf.appifood.domain.model.Produto
import com.andreaaf.appifood.helper.TipoLayout
import com.squareup.picasso.Picasso

class ProdutosAdapter(
    private val tipoLayout: TipoLayout,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var listaProdutos = emptyList<Produto>()
    fun adicionarLista( lista: List<Produto> ){
        listaProdutos = lista
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if( tipoLayout == TipoLayout.HORIZONTAL ){
            val inflater = LayoutInflater.from(parent.context)
            val itemViewProdutosDestaque = ItemRvProdutoDestaqueBinding.inflate(
                inflater, parent, false
            )
            return ProdutosDestaqueViewHolder( itemViewProdutosDestaque )
        }

        val inflater = LayoutInflater.from(parent.context)
        val itemViewProdutos = ItemRvProdutosBinding.inflate(
            inflater, parent, false
        )
        return ProdutosViewHolder( itemViewProdutos )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val produto = listaProdutos[position]
        when( holder ){
            is ProdutosAdapter.ProdutosViewHolder -> holder.bind( produto )
            is ProdutosAdapter.ProdutosDestaqueViewHolder -> holder.bind( produto )
        }
    }

    override fun getItemCount(): Int {
        return listaProdutos.size
    }

    inner class ProdutosDestaqueViewHolder(//ViewHolder
        private val binding: ItemRvProdutoDestaqueBinding
    ) : RecyclerView.ViewHolder( binding.root ){
        fun bind( produto: Produto ){
            binding.textTituloDestaque.text = produto.titulo

            if( produto.precoDesconto.isNotEmpty() ){
                binding.textPreco1Destaque.text = produto.precoDesconto
                binding.textPreco2Destaque.text = produto.preco
            }else{
                binding.textPreco1Destaque.text = produto.preco
            }

            binding.clItemProdutoDestaque.setOnClickListener {
                onClick()
            }

            if( produto.urlImagem.isNotEmpty() ){
                Picasso.get()
                    .load( produto.urlImagem )
                    .into( binding.imageProdutoDestaque )

            }
        }
    }

    inner class ProdutosViewHolder(//ViewHolder
        private val binding: ItemRvProdutosBinding
    ) : RecyclerView.ViewHolder( binding.root ){
        fun bind( produto: Produto ){

            binding.textTituloProduto.text = produto.titulo
            binding.textDescricaoProduto.text = produto.descricao
            binding.textPrecoProduto.text = produto.preco

            binding.clItemProduto.setOnClickListener {
                onClick()
            }

            if( produto.urlImagem.isNotEmpty() ){
                Picasso.get()
                    .load( produto.urlImagem )
                    .into( binding.imageProduto )

            }
        }
    }

}