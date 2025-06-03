package com.example.aplicativotcc.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.model.repositorio.TarefasRepositorio
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.aplicativotcc.model.Tarefa
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.ui.theme.Red


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetalhesTarefa(
    navController: NavController,
    titulo: String,
    descricao: String,
    dataInicial: String,
    dataFinal: String,
    duracao: String,
    prioridade: Int,
    finalizada: Boolean
) {
    val context = LocalContext.current
    val tarefasRepositorio = remember { TarefasRepositorio(context) }
    val showDeleteDialog = remember { mutableStateOf(false) } // Para confirmação de exclusão
    val showFinalizeDialog = remember { mutableStateOf(false) } // Para confirmação de finalização

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "Detalhes da Tarefa",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        backgroundColor = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Título: $titulo", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Descrição:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(descricao, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Data Inicial: $dataInicial", fontSize = 16.sp)
                    Text("Data Final: $dataFinal", fontSize = 16.sp)
                    Text("Duração: $duracao", fontSize = 16.sp)
                    Text(
                        "Prioridade: ${
                            when (prioridade) {
                                1 -> "Urgente"
                                2 -> "Importante"
                                else -> "Interessante"
                            }
                        }", fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão de edição
                Button(
                    onClick = {
                        navController.navigate("editar_tarefa?titulo=$titulo&descricao=$descricao&dataInicial=$dataInicial&dataFinal=$dataFinal&duracao=$duracao&prioridade=$prioridade")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueEscuro),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Editar",
                        fontSize = 14.sp)
                }

                // Botão de excluir
                Button(
                    onClick = { showDeleteDialog.value = true }, // Mostra o diálogo de exclusão
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Excluir",
                        fontSize = 14.sp)
                }

                // Botão de finalizar
                Button(
                    onClick = { showFinalizeDialog.value = true }, // Mostra o diálogo de finalização
                    colors = ButtonDefaults.buttonColors(backgroundColor = GreenEscuro),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("Finalizar",
                        fontSize = 14.sp)
                }
            }
        }
    }


    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir esta tarefa?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            tarefasRepositorio.excluirTarefa(titulo)
                            navController.popBackStack()
                            showDeleteDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                    ) {
                        Text("Sim")
                    }
                    Button(
                        onClick = { showDeleteDialog.value = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        )
    }


    if (showFinalizeDialog.value) {
        AlertDialog(
            onDismissRequest = { showFinalizeDialog.value = false },
            title = { Text("Confirmar Finalização") },
            text = { Text("Tem certeza de que deseja marcar esta tarefa como finalizada?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            tarefasRepositorio.atualizarTarefa(
                                titulo,
                                descricao,
                                dataInicial,
                                dataFinal,
                                duracao,
                                prioridade,
                                true
                            )
                            navController.popBackStack()
                            showFinalizeDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                    ) {
                        Text("Sim")
                    }
                    Button(
                        onClick = { showFinalizeDialog.value = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        )
    }

}
