package com.example.projeto.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto.databinding.FragmentRituaisBinding
import com.example.projeto.view.adapter.CategoriasAdapter
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.viewmodel.CategoriasViewModel

class RituaisFragment : Fragment() {

    private var _binding: FragmentRituaisBinding? = null
    private val binding get() = _binding!!

    private lateinit var rituaisViewModel: CategoriasViewModel
    private val adapter = CategoriasAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        rituaisViewModel = ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentRituaisBinding.inflate(inflater, container, false)

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
                rituaisViewModel.delete(id)
                rituaisViewModel.getRituais()
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
        rituaisViewModel.getRituais()
    }

    private fun observe() {
        rituaisViewModel.categorias.observe(viewLifecycleOwner) {
            adapter.updateCategoria(it)
        }
    }
}