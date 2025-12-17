package com.example.aplicativotcc.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aplicativotcc.viewmodel.DetalhesTarefaViewModel
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.ui.theme.Red

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesTarefa(
    navController: NavController,
    tarefaId: Int,
    viewModel: DetalhesTarefaViewModel = hiltViewModel()
) {

    val tarefa by viewModel
        .getTarefa(tarefaId)
        .collectAsState(initial = null)

    if (tarefa == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes da Tarefa",
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Título: ${tarefa!!.titulo}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Descrição:", fontWeight = FontWeight.Bold)
                    Text(tarefa!!.descricao)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Data Inicial: ${tarefa!!.dataInicial}")
                    Text("Data Final: ${tarefa!!.dataFinal}")
                    Text("Duração: ${tarefa!!.duracao}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Prioridade: " + when (tarefa!!.prioridade) {
                            1 -> "Urgente"
                            2 -> "Importante"
                            else -> "Interessante"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        navController.navigate("editar_tarefa/${tarefa!!.id}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueEscuro)
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        viewModel.excluir(tarefa!!.id)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Excluir")
                }

                Button(
                    onClick = {
                        viewModel.finalizar(tarefa!!.id)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenEscuro)
                ) {
                    Text("Finalizar")
                }
            }
        }
    }
}
