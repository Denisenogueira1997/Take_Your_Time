package com.example.aplicativotcc.view.componentes

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.R
import com.example.aplicativotcc.data.DateUtil
import com.example.aplicativotcc.data.TarefaEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarefaItem(
    tarefa: TarefaEntity,
    navController: NavController,
    onAtualizarTarefa: (TarefaEntity) -> Unit
) {
    val dateUtil = remember { DateUtil() }
    val context = LocalContext.current

    val tituloTarefa = tarefa.titulo.ifEmpty { "Tarefa sem título" }
    val tempoDiarioExibido = tarefa.tempoDiarioFixo.ifBlank { "00:00" }

    var isCheckedState by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = if (isCheckedState)
                        R.drawable.ic_check
                    else
                        R.drawable.ic_checkadd
                ),
                contentDescription = "Marcar tarefa",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isCheckedState = !isCheckedState
                        if (isCheckedState) showTimePicker = true
                    }
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .clickable {
                    navController.navigate("DetalhesTarefa/${tarefa.id}")
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {


                Text(
                    text = tituloTarefa,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 12.dp, end = 90.dp),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .width(64.dp)
                        .height(30.dp)
                        .border(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tempoDiarioExibido,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

    if (showTimePicker) {
        DisposableEffect(Unit) {
            val dialog = TimePickerDialog(
                context,
                { _, hour, minute ->
                    val minutosTrabalhados = hour * 60 + minute
                    if (minutosTrabalhados <= 0) {
                        showTimePicker = false
                        return@TimePickerDialog
                    }

                    val novaDuracao = dateUtil.calcularNovaDuracao(
                        tarefa,
                        minutosTrabalhados
                    )

                    val novoTempoDiario = dateUtil.subtrairDoTempoDiario(
                        tarefa,
                        minutosTrabalhados
                    )

                    val tarefaAtualizada = tarefa.copy(
                        duracao = novaDuracao,
                        tempoDiarioFixo = novoTempoDiario
                    )

                    onAtualizarTarefa(tarefaAtualizada)
                    showTimePicker = false
                },
                0,
                0,
                true
            )

            dialog.show()

            onDispose { dialog.dismiss() }
        }
    }
}