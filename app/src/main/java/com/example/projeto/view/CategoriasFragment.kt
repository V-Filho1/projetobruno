package com.example.projeto.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.databinding.FragmentCategoriasBinding
import com.example.projeto.view.adapter.CategoriasAdapter
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.viewmodel.CategoriasViewModel

class CategoriasFragment : Fragment() {

    private var _binding: FragmentCategoriasBinding? = null

    private val binding get() = _binding!!
    private lateinit var categoriasViewModel: CategoriasViewModel

    private val adapter = CategoriasAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, b: Bundle?
    ): View {
        categoriasViewModel =
            ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)

        //Layout
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(context)

        binding.recyclerCategorias.adapter = adapter


        val listener = object : OnCategoriaListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, CategoriasActivity::class.java)

                val bundle = Bundle()
                bundle.putInt("categoriaId", id)
                intent.putExtras(bundle)

                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                categoriasViewModel.delete(id)
                categoriasViewModel.getAll()
            }


        }

        adapter.attachListener(listener)

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        categoriasViewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        categoriasViewModel.categorias.observe(viewLifecycleOwner) {
            adapter.updateCategoria(it)
        }
    }
}