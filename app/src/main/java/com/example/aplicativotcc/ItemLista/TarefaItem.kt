import android.app.TimePickerDialog
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.model.Tarefa
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.*
import com.example.aplicativotcc.util.DateUtil
import java.time.LocalTime

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

    val timeSetListener =
        TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
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

    val todasTarefas =
        tarefasRepositorio.todastarefas.collectAsState(initial = mutableListOf()).value

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
            backgroundColor = marrom50,
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clickable {
                    navController.navigate("DetalhesTarefa?titulo=${tarefa.tarefa}&descricao=${tarefa.descricao}&dataInicial=${tarefa.dataInicial}&dataFinal=${tarefa.dataFinal}&duracao=${tarefa.duracao}&prioridade=${tarefa.prioridade}")
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
                            .border(2.dp, corBorda,shape = RoundedCornerShape(8.dp)),
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

                        showTimePicker.value = false
                    },
                    0, 0, true
                ).show()
            }
        }
    }


