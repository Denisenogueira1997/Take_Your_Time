package com.example.aplicativotcc

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicativotcc.ui.theme.AplicativoTCCTheme
import com.example.aplicativotcc.ui.theme.system.SetupSystemBars
import com.example.aplicativotcc.view.AtividadesFinalizadas
import com.example.aplicativotcc.view.CriarTarefas
import com.example.aplicativotcc.view.DetalhesTarefa
import com.example.aplicativotcc.view.EditarTarefas
import com.example.aplicativotcc.view.ListaTarefas
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AplicativoTCCTheme(darkTheme = false) {
                SetupSystemBars()
                Navegacao()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegacao() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "ListaTarefas"
    ) {


        composable("ListaTarefas") {
            ListaTarefas(navController)
        }


        composable("AtividadesFinalizadas") {
            AtividadesFinalizadas(navController)
        }


        composable("CriarTarefas") {
            CriarTarefas(navController)
        }


        composable(
            route = "DetalhesTarefa/{id}"
        ) { backStackEntry ->

            val id = backStackEntry.arguments
                ?.getString("id")
                ?.toIntOrNull() ?: 0

            DetalhesTarefa(
                navController = navController,
                tarefaId = id
            )
        }


        composable("editar_tarefa/{id}") { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("id")
                ?.toIntOrNull() ?: 0

            EditarTarefas(
                navController = navController,
                tarefaId = id
            )
        }
    }
}
