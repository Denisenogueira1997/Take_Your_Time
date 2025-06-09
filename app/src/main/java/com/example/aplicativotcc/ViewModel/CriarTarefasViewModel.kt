package com.example.aplicativotcc.ViewModel


import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicativotcc.model.repositorio.TarefasRepositorio
import com.example.aplicativotcc.view.constantes.Constantes
import com.example.aplicativotcc.model.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CriarTarefasViewModel(
    application: Application
) : AndroidViewModel(application) {

    var tituloTarefa by mutableStateOf("")
    var descricaoTarefa by mutableStateOf("")
    var selectedDateInicial by mutableStateOf("Selecionar data inicial*")
    var selectedDateFinal by mutableStateOf("Selecionar data final*")
    var selectedDuration by mutableStateOf("Duração total da atividade*")
    var selectedPriority by mutableStateOf("Selecione a prioridade*")

    private val context = getApplication<Application>().applicationContext
    private val tarefasRepositorio = TarefasRepositorio(context)
    private val dateUtil = DateUtil()

    fun salvarTarefa(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var validInput = true
            var errorMessage = StringBuilder()

            if (tituloTarefa.isEmpty()) {
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
            val dataAtual = dateUtil.getCurrentDate()
            if (selectedDateInicial < dataAtual) {
                validInput = false
                errorMessage.append("A data inicial não pode ser anterior à data atual.\n")
            }

            if (validInput) {
                val prioridade = when (selectedPriority) {
                    "Urgente" -> Constantes.urgente
                    "Importante" -> Constantes.importante
                    "Interessante" -> Constantes.interessante
                    else -> Constantes.interessante
                }
                tarefasRepositorio.salvarTarefa(
                    tituloTarefa,
                    descricaoTarefa,
                    prioridade,
                    selectedDateInicial,
                    selectedDateFinal,
                    selectedDuration
                )
                launch(Dispatchers.Main) {
                    onSuccess()
                }
            } else {
                launch(Dispatchers.Main) {
                    onError(errorMessage.toString())
                }
            }
        }
    }
}
