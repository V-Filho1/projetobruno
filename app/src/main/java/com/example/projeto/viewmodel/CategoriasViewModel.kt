package com.example.projeto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projeto.model.CategoriaModel
import com.example.projeto.repository.CategoriasRepository

class CategoriasViewModel(application: Application) : AndroidViewModel(application){

    private val repository = CategoriasRepository.getInstance(application.applicationContext)

    private val listaCategorias = MutableLiveData<List<CategoriaModel>>()
    val categorias: LiveData<List<CategoriaModel>> = listaCategorias

    fun getAll(){
        listaCategorias.value = repository.getAll()
    }

    fun getFantasmas(){
        listaCategorias.value = repository.getFantasma()
    }

    fun getRituais(){
        listaCategorias.value = repository.getRituais()
    }

    fun getCasasAssombradas(){
        listaCategorias.value = repository.getCasasAssombradas()
    }

    fun getLidos(){
        listaCategorias.value = repository.getLida()
    }

    fun getNaoLida(){
        listaCategorias.value = repository.getNaoLida()
    }

    fun getFavoritos(){
        listaCategorias.value = repository.getFavoritos()
    }

    fun delete(id: Int){
        repository.delete(id)
    }

    fun get(id: Int){
        repository.get(id)
    }
}