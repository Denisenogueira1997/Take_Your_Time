package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.data.TarefaEntity
import com.example.aplicativotcc.data.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AtividadesFinalizadasViewModel @Inject constructor(
    repositorio: TarefasRepositorio
) : ViewModel() {

    val atividadesFinalizadas: StateFlow<List<TarefaEntity>> =
        repositorio.recuperarTarefasFinalizadas()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}

