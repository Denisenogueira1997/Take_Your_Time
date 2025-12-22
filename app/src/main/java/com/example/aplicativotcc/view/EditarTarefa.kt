package com.example.aplicativotcc.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aplicativotcc.data.DateUtil
import com.example.aplicativotcc.view.componentes.CaixaDeData
import com.example.aplicativotcc.view.componentes.CaixaDeSelecao
import com.example.aplicativotcc.view.componentes.CaixaDeTexto
import com.example.aplicativotcc.view.componentes.CaixaDeTextoDuracao
import com.example.aplicativotcc.view.constantes.Constantes
import com.example.aplicativotcc.viewmodel.EditarTarefaViewModel
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarTarefas(
    navController: NavController,
    tarefaId: Int,
    viewModel: EditarTarefaViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val dateUtil = DateUtil()

    val tarefa by viewModel
        .getTarefa(tarefaId)
        .collectAsState(initial = null)

    if (tarefa == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }
    var titulo by remember { mutableStateOf(tarefa!!.titulo) }
    var descricao by remember { mutableStateOf(tarefa!!.descricao) }
    var dataInicial by remember { mutableStateOf(tarefa!!.dataInicial) }
    var dataFinal by remember { mutableStateOf(tarefa!!.dataFinal) }
    var duracao by remember { mutableStateOf(tarefa!!.duracao) }
    var prioridade by remember {
        mutableStateOf(
            when (tarefa!!.prioridade) {
                Constantes.urgente -> "Urgente"
                Constantes.importante -> "Importante"
                else -> "Interessante"
            }
        )
    }


    val calendar = Calendar.getInstance()

    val datePickerInicial = DatePickerDialog(
        context,
        { _, y, m, d ->
            dataInicial = dateUtil.createFormattedDate(d, m + 1, y)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerFinal = DatePickerDialog(
        context,
        { _, y, m, d ->
            dataFinal = dateUtil.createFormattedDate(d, m + 1, y)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePicker = TimePickerDialog(
        context,
        { _, h, m ->
            duracao = dateUtil.createFormattedTime(h, m)
        },
        0, 0, true
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Editar Tarefa",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.height(16.dp))

            CaixaDeTexto(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholderText = "Título",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            Spacer(Modifier.height(12.dp))
            CaixaDeTexto(
                value = descricao,
                onValueChange = { descricao = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp),
                placeholderText = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )

            Spacer(Modifier.height(12.dp))

            CaixaDeData(
                value = dataInicial,
                onClick = { datePickerInicial.show() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(12.dp))
            CaixaDeData(
                value = dataFinal,
                onClick = { datePickerFinal.show() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
            CaixaDeTextoDuracao(
                value = duracao,
                onClick = { timePicker.show() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
            CaixaDeSelecao(
                selectedPriority = prioridade,
                onPrioritySelected = { prioridade = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )

            val prioridadeInt = when (prioridade) {
                "Urgente" -> Constantes.urgente
                "Importante" -> Constantes.importante
                else -> Constantes.interessante
            }
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {

                Button(
                    modifier = Modifier
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,

                        ),
                    onClick = {
                        viewModel.atualizarTarefa(
                            tarefa = tarefa!!.copy(
                                titulo = titulo,
                                descricao = descricao,
                                dataInicial = dataInicial,
                                dataFinal = dataFinal,
                                duracao = duracao,
                                prioridade = prioridadeInt
                            ),
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Tarefa atualizada com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                ) {
                    Text(
                        text = "Salvar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
