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
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.BlueEscuro
import com.example.aplicativotcc.ui.theme.GreenEscuro
import com.example.aplicativotcc.ui.theme.Red
import com.example.aplicativotcc.ui.theme.marrom100
import com.example.aplicativotcc.ui.theme.marrom50


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
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {

                        Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_lista),
                                contentDescription = "Ícone lista",
                                tint = Black,
                                modifier = Modifier
                                    .size(28.dp)
                                    .align(Alignment.CenterStart)
                            )
                            Text(
                                text = "Lista de tarefas",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                },
                backgroundColor = marrom100,
                contentColor = Black
            )
        },
        backgroundColor = marrom50,
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {

                Button(
                    onClick = {
                        navController.navigate("AtividadesFinalizadas")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_estatistica),
                        contentDescription = "Ir para estatísticas",
                        tint = Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Estatística", color = Black)
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



