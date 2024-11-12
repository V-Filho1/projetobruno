package com.example.projeto.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto.databinding.FragmentCasasAssombradasBinding
import com.example.projeto.databinding.FragmentFavoritosBinding
import com.example.projeto.databinding.FragmentLidosBinding
import com.example.projeto.view.adapter.CategoriasAdapter
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.viewmodel.CategoriasViewModel

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritosViewModel: CategoriasViewModel
    private val adapter = CategoriasAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        favoritosViewModel = ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)

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
                favoritosViewModel.delete(id)
                favoritosViewModel.getFavoritos()
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
        favoritosViewModel.getFavoritos()
    }

    private fun observe() {
        favoritosViewModel.categorias.observe(viewLifecycleOwner) {
            adapter.updateCategoria(it)
        }
    }
}