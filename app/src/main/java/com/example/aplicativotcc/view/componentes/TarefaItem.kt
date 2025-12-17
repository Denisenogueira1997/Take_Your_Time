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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.R
import com.example.aplicativotcc.data.DateUtil
import com.example.aplicativotcc.data.TarefaEntity
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.ui.theme.Red
import com.example.aplicativotcc.ui.theme.White
import com.example.aplicativotcc.ui.theme.marrom50
import com.example.aplicativotcc.ui.theme.marrom900


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarefaItem(
    position: Int,
    tarefa: TarefaEntity,
    navController: NavController,
    onAtualizarTarefa: (TarefaEntity) -> Unit
) {
    val dateUtil = remember { DateUtil() }
    val context = LocalContext.current

    val tituloTarefa = tarefa.titulo.ifEmpty { "Tarefa sem título" }
    val duracaoTarefa = dateUtil.calcularTempoDiario(tarefa)

    var isCheckedState by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }


    val corBorda = when (tarefa.prioridade) {
        1 -> Red
        2 -> GreenEscuro
        3 -> BlueEscuro
        else -> Color.Gray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(50.dp)
                .padding(end = 16.dp)
                .background(color = White)
        ) {
            Image(
                painter = if (isCheckedState)
                    painterResource(id = R.drawable.ic_check)
                else
                    painterResource(id = R.drawable.ic_checkadd),
                contentDescription = "Marcar tarefa",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        isCheckedState = !isCheckedState
                        if (isCheckedState) {
                            showTimePicker = true
                        }
                    }
            )

        }

        Card(
            backgroundColor = marrom50,
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clickable {
                    navController.navigate("DetalhesTarefa/${tarefa.id}")

                }
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = tituloTarefa,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(
                            start = 8.dp,
                            end = 100.dp
                        ),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = marrom900
                )


                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(56.dp)
                        .height(32.dp)
                        .border(2.dp, corBorda, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$duracaoTarefa",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 12.sp,
                        color = marrom900
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

                    val tarefaAtualizada = tarefa.copy(
                        duracao = novaDuracao
                    )

                    onAtualizarTarefa(tarefaAtualizada)

                    showTimePicker = false
                },
                0,
                0,
                true
            )


            dialog.show()

            onDispose {
                dialog.dismiss()
            }
        }
    }
}