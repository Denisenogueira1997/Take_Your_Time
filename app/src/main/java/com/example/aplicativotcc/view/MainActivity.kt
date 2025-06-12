package com.example.aplicativotcc.view


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navegacao()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegacao() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ListaTarefas") {
        composable("ListaTarefas") { ListaTarefas(navController) }
        composable("AtividadesFinalizadas") { AtividadesFinalizadas(navController = navController) }
        composable("CriarTarefas") { CriarTarefas(navController) }
        composable("DetalhesTarefa?titulo={titulo}&descricao={descricao}&dataInicial={dataInicial}&dataFinal={dataFinal}&duracao={duracao}&prioridade={prioridade}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descricao = backStackEntry.arguments?.getString("descricao") ?: ""
            val dataInicial = backStackEntry.arguments?.getString("dataInicial") ?: ""
            val dataFinal = backStackEntry.arguments?.getString("dataFinal") ?: ""
            val duracao = backStackEntry.arguments?.getString("duracao") ?: ""
            val prioridade = backStackEntry.arguments?.getString("prioridade")?.toIntOrNull() ?: 0

            DetalhesTarefa(
                navController = navController,
                titulo = titulo,
                descricao = descricao,
                dataInicial = dataInicial,
                dataFinal = dataFinal,
                duracao = duracao,
                prioridade = prioridade,
                finalizada = false
            )
        }
        composable("editar_tarefa?titulo={titulo}&descricao={descricao}&dataInicial={dataInicial}&dataFinal={dataFinal}&duracao={duracao}&prioridade={prioridade}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
            val descricao = backStackEntry.arguments?.getString("descricao") ?: ""
            val dataInicial = backStackEntry.arguments?.getString("dataInicial") ?: ""
            val dataFinal = backStackEntry.arguments?.getString("dataFinal") ?: ""
            val duracao = backStackEntry.arguments?.getString("duracao") ?: ""
            val prioridade = backStackEntry.arguments?.getString("prioridade")?.toIntOrNull() ?: 0

            EditarTarefas(
                navController = navController,
                titulo = titulo,
                descricao = descricao,
                dataInicial = dataInicial,
                dataFinal = dataFinal,
                duracao = duracao,
                prioridade = prioridade
            )
        }
    }
}