package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aplicativotcc.data.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListaTarefasViewModel @Inject constructor(
    private val repositorio: TarefasRepositorio
) : ViewModel() {

    val tarefas = repositorio.recuperarTarefas()

}
