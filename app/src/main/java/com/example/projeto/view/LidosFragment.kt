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
import com.example.projeto.databinding.FragmentLidosBinding
import com.example.projeto.view.adapter.CategoriasAdapter
import com.example.projeto.view.listener.OnCategoriaListener
import com.example.projeto.viewmodel.CategoriasViewModel

class LidosFragment : Fragment() {

    private var _binding: FragmentLidosBinding? = null
    private val binding get() = _binding!!

    private lateinit var lidosViewModel: CategoriasViewModel
    private val adapter = CategoriasAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        lidosViewModel = ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentLidosBinding.inflate(inflater, container, false)

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
                lidosViewModel.delete(id)
                lidosViewModel.getLidos()
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
        lidosViewModel.getLidos()
    }

    private fun observe() {
        lidosViewModel.categorias.observe(viewLifecycleOwner) {
            adapter.updateCategoria(it)
        }
    }
}