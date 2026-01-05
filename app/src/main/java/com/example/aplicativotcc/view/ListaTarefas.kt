package com.example.aplicativotcc.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aplicativotcc.view.componentes.TarefaItem
import com.example.aplicativotcc.viewmodel.ListaTarefasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListaTarefas(
    navController: NavController
) {
    val viewModel: ListaTarefasViewModel = hiltViewModel()

    val listaTarefas by viewModel.tarefas.collectAsState(initial = emptyList())
    val tarefas by viewModel.tarefas.collectAsState(initial = emptyList())
    LaunchedEffect(tarefas) {
        viewModel.garantirTempoDiarioAtualizado(tarefas)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Take Your Time",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("CriarTarefas") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Criar tarefa"
                )
            }
        },

        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Task, contentDescription = null)
                    },
                    label = { Text("Tarefas") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("AtividadesFinalizadas") },
                    icon = {
                        Icon(Icons.Default.BarChart, contentDescription = null)
                    },
                    label = { Text("Finalizadas") }
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(tarefas) { _, tarefa ->
                TarefaItem(
                    tarefa = tarefa,
                    navController = navController,
                    onAtualizarTarefa = { tarefaAtualizada ->
                        viewModel.atualizarTarefa(tarefaAtualizada)
                    }
                )
            }
        }
    }
}





