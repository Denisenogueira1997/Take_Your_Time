package com.example.aplicativotcc.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.ShapeEditText


@Composable
fun CaixaDeTextoDuracao(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Text(
        text = if (value.isBlank()) "Selecionar duração" else value,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ShapeEditText.medium
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = ShapeEditText.medium
            )
            .clickable {
                focusManager.clearFocus(force = true)
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 16.dp)
    )
}
