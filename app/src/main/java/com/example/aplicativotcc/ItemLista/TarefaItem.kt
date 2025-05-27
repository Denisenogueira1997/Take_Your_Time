import android.app.TimePickerDialog
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import com.example.aplicativotcc.R
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.White
//import com.example.aplicativotcc.ui.theme.Gray
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.model.Tarefa

import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.util.DateUtil
import java.time.LocalTime
import com.example.aplicativotcc.constantes.Constantes
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.Red


@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun TarefaItem(
    position: Int,
    tarefa: Tarefa,
    navController: NavController
) {
    val dateUtil = DateUtil()
    val context = LocalContext.current
    val tarefasRepositorio = TarefasRepositorio(context)

    val tituloTarefa = tarefa.tarefa ?: "Tarefa sem título"
    val duracaoTarefa = dateUtil.calcularTempoDiario(tarefa)

    var isCheckedState by remember { mutableStateOf(false) }
    val minutosDedicados = remember { mutableStateOf(0) }
    val showTimePicker = remember { mutableStateOf(false) }

    val timeSetListener = TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
        val localTime = LocalTime.of(hourOfDay, minute)
        val novaDuracao = dateUtil.calcularNovaDuracao(tarefa, localTime)
        tarefasRepositorio.atualizarTarefa(
            tarefa.tarefa,
            tarefa.descricao,
            tarefa.dataInicial,
            tarefa.dataFinal,
            novaDuracao,
            tarefa.prioridade,
            tarefa.finalizada
        )
    }

    val todasTarefas = tarefasRepositorio.todastarefas.collectAsState(initial = mutableListOf()).value

    val prioridadeCor = when (tarefa.prioridade) {
        Constantes.urgente -> Red
        Constantes.importante -> GreenEscuro
        Constantes.interessante -> BlueEscuro
        else -> White
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
            Checkbox(
                checked = isCheckedState,
                onCheckedChange = { isChecked ->
                    isCheckedState = isChecked
                    if (isChecked) {
                        showTimePicker.value = true
                    }
                },
                modifier = Modifier.fillMaxSize(),
                colors = CheckboxDefaults.colors(
                    checkedColor = GreenEscuro,
                    uncheckedColor = White
                )
            )
        }

        Card(
            backgroundColor = prioridadeCor,
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clickable {
                    navController.navigate("DetalhesTarefa?titulo=${tarefa.tarefa}&descricao=${tarefa.descricao}&dataInicial=${tarefa.dataInicial}&dataFinal=${tarefa.dataFinal}&duracao=${tarefa.duracao}&prioridade=${tarefa.prioridade}")
                }
        ) {
            Text(
                text = tituloTarefa,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, // Deixar o texto em negrito
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Card(
            backgroundColor = White,
            modifier = Modifier
                .width(70.dp)
                .height(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center, // Alinha o conteúdo no centro do Box
                modifier = Modifier.fillMaxSize() // Faz o Box preencher todo o Card
            ) {
                Text(
                    text = "$duracaoTarefa",
                    modifier = Modifier.padding(8.dp) // Apenas ajustando o padding
                )
            }
        }
    }

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val localTime = LocalTime.of(hourOfDay, minute)
                val novaDuracao = dateUtil.calcularNovaDuracao(tarefa, localTime)

                tarefasRepositorio.atualizarTarefa(
                    tarefa.tarefa,
                    tarefa.descricao,
                    tarefa.dataInicial,
                    tarefa.dataFinal,
                    novaDuracao,
                    tarefa.prioridade,
                    tarefa.finalizada
                )


                navController.navigate("DetalhesTarefa?titulo=${tarefa.tarefa}&descricao=${tarefa.descricao}&dataInicial=${tarefa.dataInicial}&dataFinal=${tarefa.dataFinal}&duracao=$novaDuracao&prioridade=${tarefa.prioridade}")

                showTimePicker.value=false // Fecha o seletor de tempo
            },
            0, 0, true
        ).show()
    }
}
