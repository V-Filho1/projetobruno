package com.example.projeto.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//CONEXÃO COM O BANCO

class CategoriaDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

        companion object{
            private const val NAME = "projetodb"
            private const val VERSION = 1
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Categorias (" +
                "id integer primary key autoincrement," +
                "nome text," +
                "lenda text," +
                "lida integer," +
                "favorita integeresti," +
                "categoria text);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}