package com.example.aplicativotcc.view.componentes

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.Gray
import com.example.aplicativotcc.ui.theme.Green

@Composable
fun BotaoCancelar(
    onClick:()->Unit,
    modifier: Modifier,
    texto:String

) {

    Button(
        onClick ,
        modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Gray,
            contentColor = Color.Black

        )
    ) {
        Text(text= texto, fontWeight = FontWeight.Bold, fontSize = 18.sp)

    }

}