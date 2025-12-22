package com.example.aplicativotcc.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.data.DateUtil
import com.example.aplicativotcc.data.TarefasRepositorio
import com.example.aplicativotcc.view.constantes.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CriarTarefasViewModel @Inject constructor(
    application: Application,
    private val tarefasRepositorio: TarefasRepositorio
) : AndroidViewModel(application) {

    var tituloTarefa by mutableStateOf("")
    var descricaoTarefa by mutableStateOf("")
    var selectedDateInicial by mutableStateOf("Selecionar data inicial*")
    var selectedDateFinal by mutableStateOf("Selecionar data final*")
    var selectedDuration by mutableStateOf("Duração total da atividade*")
    var selectedPriority by mutableStateOf("Selecione a prioridade*")

    val dateUtil = DateUtil()


    @RequiresApi(Build.VERSION_CODES.O)
    fun salvarTarefa(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            var validInput = true
            val errorMessage = StringBuilder()

            if (tituloTarefa.isBlank()) {
                validInput = false
                errorMessage.append("O título da tarefa é obrigatório.\n")
            }

            if (selectedDateInicial == "Selecionar data inicial*") {
                validInput = false
                errorMessage.append("A data inicial é obrigatória.\n")
            }

            if (selectedDateFinal == "Selecionar data final*") {
                validInput = false
                errorMessage.append("A data final é obrigatória.\n")
            }

            if (selectedDuration == "Duração total da atividade*") {
                validInput = false
                errorMessage.append("A duração é obrigatória.\n")
            }

            if (selectedPriority == "Selecione a prioridade*") {
                validInput = false
                errorMessage.append("A prioridade é obrigatória.\n")
            }

            if (
                selectedDateInicial != "Selecionar data inicial*" &&
                selectedDateInicial.isNotBlank()
            ) {
                val hoje = LocalDate.now()
                val dataInicial = try {
                    LocalDate.parse(selectedDateInicial, dateUtil.dateFormatter)
                } catch (e: Exception) {
                    null
                }

                if (dataInicial != null && dataInicial.isBefore(hoje)) {
                    validInput = false
                    errorMessage.append("A data inicial não pode ser anterior à data atual.\n")
                }
            }


            if (
                selectedDateInicial != "Selecionar data inicial*" &&
                selectedDateFinal != "Selecionar data final*"
            ) {
                val intervaloValido =
                    dateUtil.validarIntervaloDatas(selectedDateInicial, selectedDateFinal)

                if (!intervaloValido) {
                    validInput = false
                    errorMessage.append("A data final não pode ser anterior à data inicial.\n")
                }
            }

            if (!validInput) {
                launch(Dispatchers.Main) {
                    onError(errorMessage.toString())
                }
                return@launch
            }

            val prioridade = when (selectedPriority) {
                "Urgente" -> Constantes.urgente
                "Importante" -> Constantes.importante
                "Interessante" -> Constantes.interessante
                else -> Constantes.interessante
            }

            val tempoDiarioInicial = dateUtil.calcularTempoDiarioInicial(
                dataInicial = selectedDateInicial,
                dataFinal = selectedDateFinal,
                duracao = selectedDuration
            )

            val hoje = LocalDate.now().format(dateUtil.dateFormatter)

            tarefasRepositorio.adicionar(
                titulo = tituloTarefa,
                descricao = descricaoTarefa,
                dataInicial = selectedDateInicial,
                dataFinal = selectedDateFinal,
                duracao = selectedDuration,
                prioridade = prioridade,
                tempoDiarioFixo = tempoDiarioInicial,
                dataTempoDiario = hoje
            )

            launch(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

}
