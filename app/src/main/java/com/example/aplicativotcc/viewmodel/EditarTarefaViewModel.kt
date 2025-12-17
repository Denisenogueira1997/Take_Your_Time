package com.example.aplicativotcc.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
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
class EditarTarefaViewModel @Inject constructor(
    private val repositorio: TarefasRepositorio
) : ViewModel() {

    private val dateUtil = DateUtil()

    fun getTarefa(id: Int) = repositorio.recuperarPorId(id)

    @RequiresApi(Build.VERSION_CODES.O)
    fun atualizarTarefa(
        tarefa: TarefaEntity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            val hoje = LocalDate.now()

            val dataInicial = try {
                LocalDate.parse(tarefa.dataInicial, dateUtil.dateFormatter)
            } catch (e: Exception) {
                null
            }

            val dataFinal = try {
                LocalDate.parse(tarefa.dataFinal, dateUtil.dateFormatter)
            } catch (e: Exception) {
                null
            }


            if (dataInicial != null && dataInicial.isBefore(hoje)) {
                onError("A data inicial não pode ser anterior à data atual.")
                return@launch
            }

            if (
                dataInicial != null &&
                dataFinal != null &&
                dataFinal.isBefore(dataInicial)
            ) {
                onError("A data final não pode ser anterior à data inicial.")
                return@launch
            }


            repositorio.atualizarTarefa(tarefa)
            onSuccess()
        }
    }
}
