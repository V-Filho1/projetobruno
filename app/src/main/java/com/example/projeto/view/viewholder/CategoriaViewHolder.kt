package com.example.projeto.view.viewholder

import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.databinding.RowCategoriaBinding
import com.example.projeto.model.CategoriaModel
import com.example.projeto.view.listener.OnCategoriaListener

class CategoriaViewHolder(
    private val bind: RowCategoriaBinding,
    private val listener: OnCategoriaListener
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(categoria: CategoriaModel) {
        bind.textNome.text = categoria.nome

        bind.textNome.setOnClickListener {
            listener.onClick(categoria.id)
        }

        bind.textNome.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de lenda")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim") { dialog, which ->
                    listener.onDelete(categoria.id)
                }
                .setNegativeButton("Não", null)
                .create()
                .show()

            true
        }
    }
}