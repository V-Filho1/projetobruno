package com.example.projeto.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projeto.R
import com.example.projeto.databinding.ActivityCategoriasBinding
import com.example.projeto.model.CategoriaModel
import com.example.projeto.viewmodel.CategoriasFormViewModel

class CategoriasActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var viewModel: CategoriasFormViewModel

    private var categoriaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CategoriasFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.buttonShare.setOnClickListener(this)
        binding.buttonRandom.setOnClickListener(this)
        binding.radioNaoLida.isChecked = true
        binding.radioCasasAssombradas.isChecked = true

        observer()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val nome = binding.editNome.text.toString()
            val lenda = binding.editLenda.text.toString()
            var categoria: String
            val lida: Int
            val favorita: Int
            if (binding.radioLidas.isChecked) lida = 1 else lida = 0
            if (binding.radioFavoritar.isChecked) favorita = 1 else favorita = 0
            if (binding.radioFantasmas.isChecked) categoria = "Fantasmas" else
                if (binding.radioRituais.isChecked) categoria = "Rituais" else categoria =
                    "Casas Assombradas"

            val model = CategoriaModel(categoriaId, nome, lenda, lida, favorita, categoria)

            viewModel.save(model)

        } else if (v.id == R.id.button_share) {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.setType("text/plain")

            val shareText = binding.editLenda.text.toString()
            myIntent.putExtra(Intent.EXTRA_TEXT, shareText)

            startActivity(Intent.createChooser(myIntent, "Compartilhar com"))
        } else if (v.id == R.id.button_random) {
            binding.editNome.setText("O Lobo da Lua")
            binding.editLenda.setText("Em noites de lua cheia, um lobo prateado surge na floresta. Quem o avista recebe coragem eterna, desde que mantenha silêncio.")
            binding.radioLidas.isChecked = true
            binding.radioFavoritar.isChecked = true
            binding.radioFantasmas.isChecked = true
        }
    }

    private fun observer() {
        viewModel.categorias.observe(this, Observer {
            binding.editNome.setText(it.nome)
            binding.editLenda.setText(it.lenda)
            if (it.lida == 1)
                binding.radioLidas.isChecked = true
            else
                binding.radioNaoLida.isChecked = true
            if (it.favorita == 1)
                binding.radioFavoritar.isChecked = true
            if (it.categoria == "Fantasmas")
                binding.radioFantasmas.isChecked = true
            else if (it.categoria == "Rituais")
                binding.radioRituais.isChecked = true
            else
                binding.radioCasasAssombradas.isChecked = true
        })

        viewModel.saveCategoria.observe(this, Observer {
            if (it) {
                if (categoriaId == 0)
                    Toast.makeText(applicationContext, "Inserido com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(
                        applicationContext,
                        "Atualizado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                finish()
            } else
                Toast.makeText(applicationContext, "Erro!", Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            categoriaId = bundle.getInt("categoriaId")
            viewModel.get(categoriaId)
        }
    }
}