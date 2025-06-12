package com.example.aplicativotcc.repositorio

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aplicativotcc.model.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DataSource(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "tarefas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TAREFAS = "tarefas"
        private const val COLUMN_TAREFA = "tarefa"
        private const val COLUMN_DESCRICAO = "descricao"
        private const val COLUMN_PRIORIDADE = "prioridade"
        private const val COLUMN_DATA_INICIAL = "dataInicial"
        private const val COLUMN_DATA_FINAL = "dataFinal"
        private const val COLUMN_DURACAO = "duracao"
        private const val COLUMN_FINALIZADA = "finalizada"
    }

    private val _todastarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())
    val todastarefas: StateFlow<MutableList<Tarefa>> = _todastarefas

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_TAREFAS (
                $COLUMN_TAREFA TEXT PRIMARY KEY,
                $COLUMN_DESCRICAO TEXT,
                $COLUMN_PRIORIDADE INTEGER,
                $COLUMN_DATA_INICIAL TEXT,
                $COLUMN_DATA_FINAL TEXT,
                $COLUMN_DURACAO TEXT,
                $COLUMN_FINALIZADA INTEGER
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAREFAS")
        onCreate(db)
    }


    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, dataInicial: String, dataFinal: String, duracao: String, finalizada:Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TAREFA, tarefa)
            put(COLUMN_DESCRICAO, descricao)
            put(COLUMN_PRIORIDADE, prioridade)
            put(COLUMN_DATA_INICIAL, dataInicial)
            put(COLUMN_DATA_FINAL, dataFinal)
            put(COLUMN_DURACAO, duracao)
            put(COLUMN_FINALIZADA,getIntFromBoolean(finalizada))
        }

        db.insertWithOnConflict(TABLE_TAREFAS, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
        return recuperarTarefas("SELECT * FROM $TABLE_TAREFAS WHERE $COLUMN_FINALIZADA = 0")
    }

    fun recuperarTarefasFinalizadas(): Flow<MutableList<Tarefa>>{
        return recuperarTarefas("SELECT * FROM $TABLE_TAREFAS WHERE $COLUMN_FINALIZADA <> 0")
    }

    private fun recuperarTarefas(sql: String): Flow<MutableList<Tarefa>> {
        val listaTarefas: MutableList<Tarefa> = mutableListOf()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            do {
                val tarefa = Tarefa(
                    tarefa = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAREFA)),
                    descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO)),
                    prioridade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORIDADE)),
                    dataInicial = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA_INICIAL)),
                    dataFinal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA_FINAL)),
                    duracao = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURACAO)),
                    finalizada = getBooleanFromInt(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FINALIZADA)))
                )
                listaTarefas.add(tarefa)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        _todastarefas.value = listaTarefas
        return todastarefas
    }


    fun atualizarTarefa(
        titulo: String,
        descricao: String,
        dataInicial: String,
        dataFinal: String,
        duracao: String,
        prioridade: Int,
        finalizada: Boolean
    ) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DESCRICAO, descricao)
            put(COLUMN_PRIORIDADE, prioridade)
            put(COLUMN_DATA_INICIAL, dataInicial)
            put(COLUMN_DATA_FINAL, dataFinal)
            put(COLUMN_DURACAO, duracao)
            put(COLUMN_FINALIZADA, getIntFromBoolean(finalizada))
        }

        db.update(TABLE_TAREFAS, values, "$COLUMN_TAREFA = ?", arrayOf(titulo))
        db.close()
    }


    fun excluirTarefa(titulo: String) {
        val db = writableDatabase
        db.delete(TABLE_TAREFAS, "$COLUMN_TAREFA = ?", arrayOf(titulo))
        db.close()
    }

    private fun getBooleanFromInt(integer: Int): Boolean {
        return integer != 0
    }

    private fun getIntFromBoolean(boolean: Boolean): Int {
        return if(boolean) 1 else 0

    }

}