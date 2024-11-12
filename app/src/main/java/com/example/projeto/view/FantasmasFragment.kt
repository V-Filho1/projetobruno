package com.example.projeto.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto.databinding.FragmentFantasmasBinding
import com.example.projeto.view.adapter.CategoriasAdapter
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.viewmodel.CategoriasViewModel

class FantasmasFragment : Fragment() {

    private var _binding: FragmentFantasmasBinding? = null
    private val binding get() = _binding!!

    private lateinit var fantasmasViewModel: CategoriasViewModel
    private val adapter = CategoriasAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        fantasmasViewModel = ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentFantasmasBinding.inflate(inflater, container, false)

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
                fantasmasViewModel.delete(id)
                fantasmasViewModel.getFantasmas()
            }

        }
        adapter.attachListener(listener)
        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fantasmasViewModel.getFantasmas()
    }

    private fun observe() {
        fantasmasViewModel.categorias.observe(viewLifecycleOwner) {
            adapter.updateCategoria(it)
        }
    }
}