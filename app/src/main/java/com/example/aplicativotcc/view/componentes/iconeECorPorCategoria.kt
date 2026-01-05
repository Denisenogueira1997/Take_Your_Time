package com.example.aplicativotcc.view.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aplicativotcc.view.constantes.Constantes

@Composable
fun iconeECorPorPrioridade(prioridade: Int): Pair<ImageVector, Color> {
    return when (prioridade) {
        Constantes.urgente ->
            Icons.Default.Warning to MaterialTheme.colorScheme.error

        Constantes.importante ->
            Icons.Default.Star to MaterialTheme.colorScheme.primary

        Constantes.interessante ->
            Icons.Default.Lightbulb to MaterialTheme.colorScheme.secondary

        else ->
            Icons.Default.Lightbulb to MaterialTheme.colorScheme.onSurface
    }
}
