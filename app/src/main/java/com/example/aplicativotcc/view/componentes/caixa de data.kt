import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.ShapeEditText
import com.example.aplicativotcc.ui.theme.White

@Composable
fun CaixaDeData(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (value.isEmpty()) "Clique para selecionar a data *" else value,
        fontSize = 18.sp,
        color = Black,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = Black,
                shape = ShapeEditText.medium
            )
            .background(White, shape = ShapeEditText.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp)
    )
}