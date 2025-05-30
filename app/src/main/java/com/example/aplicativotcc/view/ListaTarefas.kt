import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.R
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.Gray
import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.ui.theme.Red
import com.example.aplicativotcc.ui.theme.White


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun ListaTarefas(
    navController: NavController
) {
    val context = LocalContext.current
    val tarefasRepositorio = TarefasRepositorio(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de tarefas",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = White,
                contentColor = Black
            )
        },
        backgroundColor = Gray,
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate("AtividadesFinalizadas")
                        }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                        contentDescription = "Ir para a AtividadesFinalizadas",
                        tint = Black
                    )
                }

                FloatingActionButton(
                    onClick = { navController.navigate("CriarTarefas") },
                    backgroundColor = Color.White,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                        contentDescription = "Criar tarefa",
                        tint = Black
                    )
                }
            }
        }
    ) { paddingValues ->
        val listaTarefas = tarefasRepositorio.recuperarTarefas().collectAsState(mutableListOf()).value

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorLegend(color = Red, text = "Urgente")
                ColorLegend(color = GreenEscuro, text = "Importante")
                ColorLegend(color = BlueEscuro, text = "Interessante")
            }

            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                itemsIndexed(listaTarefas) { position, tarefa ->
                    TarefaItem(position = position, tarefa = tarefa, navController = navController)
                }
            }
        }
    }
}


@Composable
fun ColorLegend(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
