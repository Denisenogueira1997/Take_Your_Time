package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.model.Tarefa
import com.example.aplicativotcc.model.repositorio.TarefasRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListaTarefasViewModel(
    private val repositorio: TarefasRepositorio
) : ViewModel() {

    private val _listaTarefas = MutableStateFlow<List<Tarefa>>(emptyList())
    val listaTarefas: StateFlow<List<Tarefa>> = _listaTarefas

    init {
        viewModelScope.launch {
            repositorio.recuperarTarefas().collectLatest {
                _listaTarefas.value = it
            }
        }
    }
}

