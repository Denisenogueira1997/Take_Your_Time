package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.data.TarefaEntity
import com.example.aplicativotcc.data.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalhesTarefaViewModel @Inject constructor(
    private val repositorio: TarefasRepositorio
) : ViewModel() {

    fun getTarefa(id: Int): Flow<TarefaEntity> {
        return repositorio.recuperarPorId(id)
    }

    fun excluir(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tarefa = repositorio.recuperarPorIdOnce(id)
            repositorio.excluir(tarefa)
        }
    }

    fun finalizar(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositorio.finalizar(id)
        }
    }
}
