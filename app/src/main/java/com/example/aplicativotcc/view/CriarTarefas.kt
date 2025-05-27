package com.example.aplicativotcc.view

import CaixaDeData
import CaixaDeSelecao
import CaixaDeTextoDuracao
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.componentes.Botao
import com.example.aplicativotcc.componentes.BotaoCancelar
import com.example.aplicativotcc.componentes.CaixaDeTexto
import com.example.aplicativotcc.constantes.Constantes
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.Black
//import com.example.aplicativotcc.ui.theme.Gray
import com.example.aplicativotcc.ui.theme.Green
import com.example.aplicativotcc.ui.theme.White
import com.example.aplicativotcc.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CriarTarefas(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val tarefasRepositorio = TarefasRepositorio(context)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = White,
                title = {
                    Text(
                        text = "Criar tarefa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) {
        var tituloTarefa by remember { mutableStateOf("") }
        var descricaoTarefa by remember { mutableStateOf("") }
        var selectedDateInicial by remember { mutableStateOf("Selecionar data inicial*") }
        var selectedDateFinal by remember { mutableStateOf("Selecionar data final*") }
        var selectedDuration by remember { mutableStateOf("Duração total da atividade*") }
        var selectedPriority by remember { mutableStateOf("Selecione a prioridade*") }


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateUtil = DateUtil()

        val datePickerDialogInicial = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val dateSelected = dateUtil.createFormattedDate(selectedDay , selectedMonth + 1, selectedYear)
                if(dateUtil.isDateBefore(dateSelected, selectedDateFinal)){
                    Toast.makeText(context, "A data inicial não pode ser depois da data final", Toast.LENGTH_LONG).show()
                } else if (dateUtil.isDateAfter(dateUtil.getCurrentDate(), dateSelected)) {
                    selectedDateInicial = dateSelected
                } else {
                    Toast.makeText(context, "A data inicial é anterior a data atual.", Toast.LENGTH_LONG).show()
                }
            },
            year, month, day
        )

        val datePickerDialogFinal = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val dateSelected = dateUtil.createFormattedDate(selectedDay , selectedMonth + 1, selectedYear)
                if (selectedDateInicial != "Selecionar data inicial*" && dateUtil.isDateAfter(selectedDateInicial, dateSelected)) {
                    selectedDateFinal = dateSelected
                } else {
                    Toast.makeText(context, "A data final não pode ser anterior à data inicial.", Toast.LENGTH_LONG).show()
                }
            },
            year, month, day
        )

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                selectedDuration = dateUtil.createFormattedTime(hour, minute)
            },
            0, 0, true
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CaixaDeTexto(
                value = tituloTarefa,
                onValueChange = { tituloTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 40.dp, 20.dp, 0.dp),
                label = "Titulo da Tarefa *",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            CaixaDeTexto(
                value = descricaoTarefa,
                onValueChange = { descricaoTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = "Descrição da Tarefa (opcional)",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )

            CaixaDeData(
                value = selectedDateInicial,
                onClick = { datePickerDialogInicial.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )

            CaixaDeData(
                value = selectedDateFinal,
                onClick = { datePickerDialogFinal.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )

            CaixaDeTextoDuracao(
                value = selectedDuration,
                onClick = { timePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )
            Text(
                text = "A duração total será dividida pelo número de dias entre a data inicial e a data final, para obter o tempo diário dedicado à atividade",
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp)  // Ajuste do padding conforme necessário
            )

            CaixaDeSelecao(
                selectedPriority = selectedPriority,
                onPrioritySelected = { selectedPriority = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Botao(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            // Verificação de campos obrigatórios
                            var validInput = true
                            var errorMessage = ""

                            if (tituloTarefa.isEmpty()) {
                                validInput = false
                                errorMessage += "O título da tarefa é obrigatório.\n"
                            }
                            if (selectedDateInicial == "Selecionar data inicial*") {
                                validInput = false
                                errorMessage += "A data inicial é obrigatória.\n"
                            }
                            if (selectedDateFinal == "Selecionar data final*") {
                                validInput = false
                                errorMessage += "A data final é obrigatória.\n"
                            }
                            if (selectedDuration == "Selecionar duração (hh:mm) *") {
                                validInput = false
                                errorMessage += "A duração é obrigatória.\n"
                            }
                            if (selectedPriority == "Selecione a prioridade*") {
                                validInput = false
                                errorMessage += "A prioridade é obrigatória.\n"
                            }
                            val dataAtual = dateUtil.getCurrentDate() // Obtém a data atual
                            if (selectedDateInicial < dataAtual) {
                                validInput = false
                                errorMessage += "A data inicial não pode ser anterior à data atual.\n"
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
                                    descricaoTarefa,  // Campo opcional
                                    prioridade,
                                    selectedDateInicial,
                                    selectedDateFinal,
                                    selectedDuration
                                )

                                launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Sucesso ao criar a atividade",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                            } else {

                                launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        errorMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 8.dp),
                    texto = "Criar",
                )

                BotaoCancelar(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 8.dp),
                    texto = "Cancelar",




                    )

            }
        }
    }
}


