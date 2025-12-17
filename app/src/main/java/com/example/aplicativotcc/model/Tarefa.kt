package com.example.aplicativotcc.model

data class Tarefa(
    val id: Int = 0,
    val titulo: String = "",
    val descricao: String = "",
    val dataInicial: String = "",
    val dataFinal: String = "",
    val duracao: String = "",
    val prioridade: Int = 0,
    val finalizada: Boolean = false
)
