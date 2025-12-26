package com.example.aplicativotcc.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.ShapeEditText


@Composable
fun CaixaDeSelecao(
    selectedPriority: String,
    onPrioritySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                focusManager.clearFocus(force = true)
                expanded = true
            }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = ShapeEditText.medium
            )
            .border(
                width = 1.dp,
                color = if (expanded)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline,
                shape = ShapeEditText.medium
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = selectedPriority,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Abrir Menu",
                tint = if (expanded)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Urgente",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    onPrioritySelected("Urgente")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        "Importante",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    onPrioritySelected("Importante")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        "Interessante",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    onPrioritySelected("Interessante")
                    expanded = false
                }
            )
        }
    }
}
