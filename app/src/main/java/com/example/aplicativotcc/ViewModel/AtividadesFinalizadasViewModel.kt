package com.example.aplicativotcc.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.model.repositorio.TarefasRepositorio
import com.example.aplicativotcc.model.Tarefa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AtividadesFinalizadasViewModel(
    private val repository: TarefasRepositorio
) : ViewModel() {

    private val _atividadesFinalizadas = MutableStateFlow<List<Tarefa>>(emptyList())
    val atividadesFinalizadas: StateFlow<List<Tarefa>> = _atividadesFinalizadas

    init {
        carregarTarefasFinalizadas()
    }

    private fun carregarTarefasFinalizadas() {
        viewModelScope.launch {
            repository.recuperarTarefasFinalizadas().collect {
                _atividadesFinalizadas.value = it
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repository = TarefasRepositorio(context)
            return AtividadesFinalizadasViewModel(repository) as T
        }
    }
}
