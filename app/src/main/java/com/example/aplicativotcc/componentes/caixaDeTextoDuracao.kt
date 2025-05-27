import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.Blue
import com.example.aplicativotcc.ui.theme.ShapeEditText
import com.example.aplicativotcc.ui.theme.White

@Composable
fun CaixaDeTextoDuracao(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = value,
        fontSize = 18.sp,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)  // Altura consistente com as outras caixas
            .border(1.dp, Black, shape = ShapeEditText.medium) // Borda e forma consistentes
            .background(White, shape = ShapeEditText.medium)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 16.dp), // Padding ajustado
        color = Black
    )
}