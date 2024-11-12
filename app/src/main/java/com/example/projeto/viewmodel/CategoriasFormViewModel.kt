package com.example.projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projeto.model.CategoriaModel
import com.example.projeto.repository.CategoriasRepository

class CategoriasFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CategoriasRepository.getInstance(application)

    private val categoriasModel = MutableLiveData<CategoriaModel>()
    val categorias: LiveData<CategoriaModel> = categoriasModel


    private val _saveCategoria = MutableLiveData<Boolean>()
    val saveCategoria: LiveData<Boolean> = _saveCategoria

    fun save(categoria: CategoriaModel) {
        if (categoria.id == 0)
            _saveCategoria.value = repository.insert(categoria)
        else
            _saveCategoria.value = repository.update(categoria)
    }

    fun get(id: Int) {
        categoriasModel.value = repository.get(id)
    }

}