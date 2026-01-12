package com.example.aplicativotcc.view.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.aplicativotcc.R
import com.example.aplicativotcc.view.constantes.Constantes

@Composable
fun iconeECorPorPrioridade(prioridade: Int): Painter {
    return when (prioridade) {
        Constantes.urgente ->
            painterResource(id = R.drawable.urgente)

        Constantes.importante ->
            painterResource(R.drawable.importante)

        Constantes.interessante ->
            painterResource(R.drawable.interessante)

        else ->
            painterResource(R.drawable.interessante)
    }
}
