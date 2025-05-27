package com.example.aplicativotcc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.Gray

@Composable
fun AtividadesFinalizadas(navController: NavController) {
    val context = LocalContext.current
    val tarefasRepositorio = TarefasRepositorio(context)

    // Observa as atividades finalizadas como um estado
    val atividadesFinalizadas = tarefasRepositorio.recuperarTarefasFinalizadas().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // Altura padrão para um AppBar
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("ListaTarefas") {
                                popUpTo("ListaTarefas") { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar para Lista de Tarefas",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // Espaço entre a seta e o texto
                    Text(
                        text = "Atividades Finalizadas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        },
        backgroundColor = Gray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (atividadesFinalizadas.value.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp) // Define uma altura menor para o quadrado
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Vazio",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    itemsIndexed(atividadesFinalizadas.value) { _, atividade ->
                        Text(
                            text = atividade.tarefa ?: "Tarefa sem título",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}



