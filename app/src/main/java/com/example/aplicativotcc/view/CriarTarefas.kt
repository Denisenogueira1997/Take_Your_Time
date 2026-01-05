package com.example.aplicativotcc.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aplicativotcc.view.componentes.Botao
import com.example.aplicativotcc.view.componentes.BotaoCancelar
import com.example.aplicativotcc.view.componentes.CaixaDeData
import com.example.aplicativotcc.view.componentes.CaixaDeSelecao
import com.example.aplicativotcc.view.componentes.CaixaDeTexto
import com.example.aplicativotcc.view.componentes.CaixaDeTextoDuracao
import com.example.aplicativotcc.viewmodel.CriarTarefasViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CriarTarefas(
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel: CriarTarefasViewModel = hiltViewModel()

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val datePickerInicial = DatePickerDialog(
        context, { _, y, m, d ->
            viewModel.selectedDateInicial = viewModel.dateUtil.createFormattedDate(d, m + 1, y)
        }, year, month, day
    )

    val datePickerFinal = DatePickerDialog(
        context, { _, y, m, d ->
            viewModel.selectedDateFinal = viewModel.dateUtil.createFormattedDate(d, m + 1, y)
        }, year, month, day
    )

    val timePickerDialog = TimePickerDialog(
        context, { _, hour: Int, minute: Int ->
            viewModel.selectedDuration = viewModel.dateUtil.createFormattedTime(hour, minute)
        }, 0, 0, true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Criar tarefa", style = MaterialTheme.typography.titleLarge
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {

            CaixaDeTexto(
                value = viewModel.tituloTarefa,
                onValueChange = { viewModel.tituloTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 40.dp, 20.dp, 0.dp),
                placeholderText = "Titulo da Tarefa *",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            CaixaDeTexto(
                value = viewModel.descricaoTarefa,
                onValueChange = { viewModel.descricaoTarefa = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                placeholderText = "Descrição da Tarefa (opcional)",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )


            CaixaDeData(
                value = viewModel.selectedDateInicial,
                onClick = { datePickerInicial.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )


            CaixaDeData(
                value = viewModel.selectedDateFinal,
                onClick = { datePickerFinal.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )


            CaixaDeTextoDuracao(
                value = viewModel.selectedDuration,
                onClick = { timePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp)
            )

            Text(
                text = "A duração total será dividida pelo número de dias entre as datas.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(20.dp, 8.dp, 20.dp, 0.dp)
            )

            CaixaDeSelecao(
                selectedPriority = viewModel.selectedPriority,
                onPrioritySelected = { viewModel.selectedPriority = it },
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
                BotaoCancelar(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 8.dp),
                    texto = "Cancelar",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Botao(
                    onClick = {
                        viewModel.salvarTarefa(onSuccess = {
                            Toast.makeText(
                                context, "Sucesso ao criar a atividade", Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        }, onError = {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        })
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(end = 8.dp),
                    texto = "Criar",
                )


            }
        }
    }
}


