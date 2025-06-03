package com.example.aplicativotcc.model.repositorio

import android.content.Context
import com.example.aplicativotcc.model.Tarefa
import com.example.aplicativotcc.repositorio.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TarefasRepositorio(context: Context) {
    private val dataSource = DataSource(context)

    private val _todastarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())
    val todastarefas: StateFlow<MutableList<Tarefa>> = _todastarefas

    // Lista para armazenar as tarefas "excluídas" temporariamente (lixeira)
    private val tarefasNaLixeira = mutableListOf<Tarefa>()

    // Método para salvar uma nova tarefa
    fun salvarTarefa(
        tarefa: String,
        descricao: String,
        prioridade: Int,
        dataInicial: String,
        dataFinal: String,
        duracao: String
    ) {
        dataSource.salvarTarefa(tarefa, descricao, prioridade, dataInicial, dataFinal, duracao, finalizada = false)
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>> {
        return dataSource.recuperarTarefas()
    }

    fun recuperarTarefasFinalizadas(): Flow<MutableList<Tarefa>> {
        return dataSource.recuperarTarefasFinalizadas()
    }


    fun atualizarTarefa(
        titulo: String?,
        novaDescricao: String?,
        novaDataInicial: String?,
        novaDataFinal: String?,
        novaDuracao: String?,
        novaPrioridade: Int,
        finalizada : Boolean
    ) {
        val tituloTarefa = titulo ?: "Título não fornecido"
        val descricaoTarefa = novaDescricao ?: "Descrição não fornecida"
        val dataInicial = novaDataInicial ?: "Data não fornecida"
        val dataFinal = novaDataFinal ?: "Data não fornecida"
        val duracao = novaDuracao ?: "Duração não fornecida"


        dataSource.atualizarTarefa(
            tituloTarefa,
            descricaoTarefa,
            dataInicial,
            dataFinal,
            duracao,
            novaPrioridade,
            finalizada
        )
    }


    fun excluirTarefa(titulo: String?) {
        val tituloTarefa = titulo ?: "Título não fornecido"
        dataSource.excluirTarefa(tituloTarefa)
        recuperarTarefas()
    }





}