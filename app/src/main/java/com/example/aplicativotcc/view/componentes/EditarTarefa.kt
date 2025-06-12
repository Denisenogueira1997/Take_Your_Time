package com.example.aplicativotcc.view

import CaixaDeData
import CaixaDeTextoDuracao
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.view.componentes.Botao
import com.example.aplicativotcc.view.componentes.BotaoCancelar
import com.example.aplicativotcc.view.componentes.CaixaDeTexto
import com.example.aplicativotcc.view.constantes.Constantes
import com.example.aplicativotcc.model.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.White
import com.example.aplicativotcc.model.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun EditarTarefas(
    navController: NavController,
    titulo: String,
    descricao: String,
    dataInicial: String,
    dataFinal: String,
    duracao: String,
    prioridade: Int
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
                        text = "Editar Tarefa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                var tituloTarefa by remember { mutableStateOf(titulo) }
                var descricaoTarefa by remember { mutableStateOf(descricao) }
                var selectedDateInicial by remember { mutableStateOf(dataInicial) }
                var selectedDateFinal by remember { mutableStateOf(dataFinal) }
                var selectedDuration by remember { mutableStateOf(duracao) }
                var selectedPriority by remember { mutableStateOf("") }


                selectedPriority = when (prioridade) {
                    Constantes.urgente -> "Urgente"
                    Constantes.importante -> "Importante"
                    Constantes.interessante -> "Interessante"
                    else -> "Selecionar prioridade*" // Valor padrão
                }


                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val dateUtil = DateUtil()

                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                      val dateInicial = dateUtil.createFormattedDate(selectedDay, selectedMonth + 1, selectedYear)
                        if(dateUtil.isDateBefore(dateInicial, selectedDateFinal)){
                            Toast.makeText(context, "A data inicial não pode ser depois da data final", Toast.LENGTH_LONG).show()
                        } else if (dateUtil.isDateAfter(dateUtil.getCurrentDate(), dateInicial)) {
                            selectedDateInicial = dateInicial
                        } else {
                            Toast.makeText(context, "A data inicial é anterior a data atual.", Toast.LENGTH_LONG).show()
                        }
                    },
                    year, month, day
                )

                val datePickerDialogFinal = DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                      val dateFinal = dateUtil.createFormattedDate(selectedDay, selectedMonth + 1, selectedYear)
                        if (selectedDateInicial != "Selecionar data inicial*" && dateUtil.isDateAfter(selectedDateInicial, dateFinal)) {
                            selectedDateFinal =  dateFinal
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 40.dp, 20.dp, 0.dp)
                        .clickable {
                            Toast.makeText(context, "O título não é editável.", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    CaixaDeTexto(
                        value = tituloTarefa,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = "Título da Tarefa *",
                        maxLines = 1,
                        keyboardType = KeyboardType.Text,
                        readOnly = true,

                    )
                }


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
                    onClick = { datePickerDialog.show() },
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
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color.White)
                        .padding(8.dp)
                        .clickable {
                            Toast.makeText(context, "A prioridade não pode ser alterada.", Toast.LENGTH_SHORT).show()
                        }
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        text = selectedPriority,
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                             .padding(8.dp)
                            .background(Color.Gray),
                        textAlign = TextAlign.Center
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Botao(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                var validInput = true
                                var errorMessage = ""

                                if (validInput) {
                                    Log.d("EditarTarefas", "Atualizando tarefa: $tituloTarefa, $descricaoTarefa, $selectedPriority, $selectedDateInicial, $selectedDateFinal, $selectedDuration")

                                    val prioridadeAtualizada: Int = when (selectedPriority) {
                                        "Urgente" -> Constantes.urgente
                                        "Importante" -> Constantes.importante
                                        "Interessante" -> Constantes.interessante
                                        else -> Constantes.interessante // Fallback padrão
                                    }


                                    tarefasRepositorio.atualizarTarefa(
                                        tituloTarefa,
                                        descricaoTarefa,
                                        selectedDateInicial,
                                        selectedDateFinal,
                                        selectedDuration,
                                        prioridadeAtualizada,
                                        false
                                    )

                                    launch(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "Sucesso ao editar a tarefa",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                        navController.navigate("listaTarefas") {

                                            popUpTo("editarTarefa") { inclusive = true }
                                        }
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
                        texto = "Salvar",
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
    )
}
