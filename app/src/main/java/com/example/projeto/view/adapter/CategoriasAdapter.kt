package com.example.projeto.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.databinding.RowCategoriaBinding
import com.example.projeto.model.CategoriaModel
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.view.viewholder.CategoriaViewHolder

class CategoriasAdapter : RecyclerView.Adapter<CategoriaViewHolder>() {

    private var categoriaList: List<CategoriaModel> = listOf()
    private lateinit var listener: OnCategoriaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val item = RowCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriaViewHolder(item, listener)
    }

    //Liga/Desliga os dados de uma RecycleView
    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(categoriaList[position])
    }

    override fun getItemCount(): Int {
        return categoriaList.count()
    }

    fun updateCategoria(list: List<CategoriaModel>){
        categoriaList = list
        notifyDataSetChanged()
    }

    fun attachListener(categoriaListener: OnCategoriaListener){
        listener = categoriaListener
    }
}