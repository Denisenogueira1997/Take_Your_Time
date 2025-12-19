package com.example.aplicativotcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.data.DateUtil
import com.example.aplicativotcc.data.TarefaEntity
import com.example.aplicativotcc.data.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListaTarefasViewModel @Inject constructor(
    private val repositorio: TarefasRepositorio
) : ViewModel() {

    val tarefas = repositorio.recuperarTarefas()
    private val dateUtil = DateUtil()


    fun garantirTempoDiarioAtualizado(lista: List<TarefaEntity>) {
        viewModelScope.launch {
            lista.forEach { tarefa ->

                val hoje = LocalDate.now().format(dateUtil.dateFormatter)

                if (tarefa.dataTempoDiario != hoje) {

                    val novoTempo = dateUtil.calcularTempoDiario(tarefa)

                    repositorio.atualizarTarefa(
                        tarefa.copy(
                            tempoDiarioFixo = novoTempo,
                            dataTempoDiario = hoje
                        )
                    )
                }
            }
        }
    }

    fun atualizarTarefa(tarefa: TarefaEntity) {
        viewModelScope.launch {
            repositorio.atualizarTarefa(tarefa)
        }
    }

}