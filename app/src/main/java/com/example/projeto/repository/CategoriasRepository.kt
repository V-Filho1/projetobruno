package com.example.projeto.repository

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.example.projeto.model.CategoriaModel

//MANIPULAÇÃO DE DADOS

//CAMADA DE REPOSITÓRIO
//Se a classe não for private, o singleton não funciona

class CategoriasRepository private constructor(context: Context) {

    private val categoriaDataBase = CategoriaDataBase(context)

    //SINGLETON -> Controle as instancias(garante que terá apenas uma)

    //Impede com que seja instanciado mais de uma vez
    //É importante usar o singleton para manter um único ponto de acesso, evitando que diferentes partes do sistema façam alterações conflitantes ao recurso.

    companion object {
        private lateinit var repository: CategoriasRepository

        fun getInstance(context: Context): CategoriasRepository {
            if (!::repository.isInitialized) {
                repository = CategoriasRepository(context)
            }
            return repository
        }
    }

    fun insert(categoria: CategoriaModel): Boolean {
        try {
            val db = categoriaDataBase.writableDatabase
            val values = ContentValues()
            values.put("nome", categoria.nome)
            values.put("lenda", categoria.lenda)
            values.put("lida", categoria.lida)
            values.put("favorita", categoria.favorita)
            values.put("categoria", categoria.categoria)

            db.insert("Categorias", null, values)
            //Toast.makeText(, "Lenda inserida com sucesso!", Toast.LENGTH_SHORT).show();
            return true
        } catch (e: Exception) {
            return false
        }

    }

    fun update(categoria: CategoriaModel): Boolean {
        try {
            val db = categoriaDataBase.writableDatabase

            val selection = "id = ?"
            val args = arrayOf(categoria.id.toString())

            val values = ContentValues()
            values.put("nome", categoria.nome)
            values.put("lenda", categoria.lenda)
            values.put("lida", categoria.lida)
            values.put("favorita", categoria.favorita)
            values.put("categoria", categoria.categoria)

            db.update("Categorias", values, selection, args)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun delete(id: Int) {
        try {
            val db = categoriaDataBase.writableDatabase

            val selection = "id = ?"
            val args = arrayOf(id.toString())

            db.delete("Categorias", selection, args)
        } catch (e: Exception) {

        }
    }

    fun getAll(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase
            val projection = arrayOf("id", "nome", "lenda", "lida", "favorita", "categoria")

            val cursor = db.query("Categorias", projection, null, null, null, null, null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun get(id: Int): CategoriaModel? {

        var categoria: CategoriaModel? = null
        try {

            val db = categoriaDataBase.readableDatabase
            val projection = arrayOf("id", "nome", "lenda", "lida", "favorita", "categoria")

            val selection = "id = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query("Categorias", projection, selection, args, null, null, null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val _categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    categoria = CategoriaModel(id, nome, lenda, lida, favorita, _categoria)

                }
            }
            cursor.close()
        } catch (e: Exception) {
            return categoria
        }
        return categoria
    }

    fun getLida(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE lida = 1",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getFavoritos(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE favorita = 1",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getNaoLida(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE lida = 0",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getFantasma(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase

            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE categoria = 'Fantasmas'",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getRituais(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase
            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE categoria = 'Rituais'",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getCasasAssombradas(): List<CategoriaModel> {

        val list = mutableListOf<CategoriaModel>()
        try {

            val db = categoriaDataBase.readableDatabase
            val cursor = db.rawQuery(
                "SELECT id, nome, lenda, lida, favorita, categoria FROM Categorias WHERE categoria = 'Casas Assombradas'",
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val lenda = cursor.getString(cursor.getColumnIndexOrThrow("lenda"))
                    val lida = cursor.getInt(cursor.getColumnIndexOrThrow("lida"))
                    val favorita = cursor.getInt(cursor.getColumnIndexOrThrow("favorita"))
                    val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

                    val historia = CategoriaModel(id, nome, lenda, lida, favorita, categoria)
                    list.add(historia)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }



}