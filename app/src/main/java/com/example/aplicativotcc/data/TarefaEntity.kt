package com.example.aplicativotcc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas")
data class TarefaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val descricao: String = "",
    val dataInicial: String = "",
    val dataFinal: String = "",
    val duracao: String = "",
    val prioridade: Int = 0,
    val finalizada: Boolean = false


)

