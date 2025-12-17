package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.data.TarefaEntity
import com.example.aplicativotcc.data.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarefasViewModel @Inject constructor(
    private val repositorio: TarefasRepositorio
) : ViewModel() {


    val tarefas = repositorio.recuperarTarefas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val tarefasFinalizadas = repositorio.recuperarTarefasFinalizadas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun recuperarPorId(id: Int) =
        repositorio.recuperarPorId(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TarefaEntity())

    fun adicionar(
        titulo: String,
        descricao: String,
        dataInicial: String,
        dataFinal: String,
        duracao: String,
        prioridade: Int
    ) {
        viewModelScope.launch {
            repositorio.adicionar(
                titulo,
                descricao,
                dataInicial,
                dataFinal,
                duracao,
                prioridade
            )
        }
    }


    fun atualizar(tarefa: TarefaEntity) {
        viewModelScope.launch {
            repositorio.atualizarTarefa(tarefa)
        }
    }

    fun excluir(tarefa: TarefaEntity) {
        viewModelScope.launch {
            repositorio.excluir(tarefa)
        }
    }
}

