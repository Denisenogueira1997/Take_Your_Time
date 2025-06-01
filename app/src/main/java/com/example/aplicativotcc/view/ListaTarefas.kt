import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicativotcc.R
import com.example.aplicativotcc.repositorio.TarefasRepositorio
import com.example.aplicativotcc.ui.theme.marrom100
import com.example.aplicativotcc.ui.theme.marrom50
import com.example.aplicativotcc.ui.theme.marrom900

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
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_lista),
                            contentDescription = "Ícone lista",
                            tint = marrom900,
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.CenterStart)
                        )
                        Text(
                            text = "Lista de tarefas",
                            fontSize = 24.sp,
                            color = marrom900,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                backgroundColor = marrom100,
                contentColor = marrom900
            )
        },
        backgroundColor = marrom50,
        bottomBar = {
            BottomAppBar(
                backgroundColor = marrom100,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {

                    TextButton(
                        onClick = {  },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = marrom50,
                            contentColor = marrom900
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_task),
                            contentDescription = "Lista de tarefas",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tarefas")
                    }


                    FloatingActionButton(
                        onClick = { navController.navigate("CriarTarefas") },
                        backgroundColor = marrom900,
                        contentColor = marrom100,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                            contentDescription = "Criar tarefa"
                        )
                    }


                    TextButton(
                        onClick = { navController.navigate("AtividadesFinalizadas") },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = marrom900
                        ),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_estatistica),
                            contentDescription = "Ir para estatísticas",
                            modifier = Modifier.size(20.dp),
                            tint = marrom900
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Estatística", color = marrom900)
                    }
                }
            }
        }
    ) { paddingValues ->
        val listaTarefas =
            tarefasRepositorio.recuperarTarefas().collectAsState(mutableListOf()).value

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                itemsIndexed(listaTarefas) { position, tarefa ->
                    TarefaItem(position = position, tarefa = tarefa, navController = navController)
                }
            }
        }
    }
}




