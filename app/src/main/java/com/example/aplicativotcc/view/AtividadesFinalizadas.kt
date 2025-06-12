package com.example.aplicativotcc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicativotcc.ViewModel.AtividadesFinalizadasViewModel
import com.example.aplicativotcc.ui.theme.Gray


@Composable
fun AtividadesFinalizadas(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AtividadesFinalizadasViewModel = viewModel(
        factory = AtividadesFinalizadasViewModel.Factory(context)
    )
    val atividadesFinalizadas by viewModel.atividadesFinalizadas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White, elevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.navigate("ListaTarefas") {
                            popUpTo("ListaTarefas") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, "Voltar", tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Atividades Finalizadas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        backgroundColor = Gray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (atividadesFinalizadas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp)
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Vazio", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                ) {
                    items(atividadesFinalizadas) { atividade ->
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




