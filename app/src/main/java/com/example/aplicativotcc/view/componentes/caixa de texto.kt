package com.example.aplicativotcc.view.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativotcc.ui.theme.Black
import com.example.aplicativotcc.ui.theme.Blue
import com.example.aplicativotcc.ui.theme.ShapeEditText
import com.example.aplicativotcc.ui.theme.White

@Composable
fun CaixaDeTexto(
    value:String,
    onValueChange :(String) -> Unit,
    modifier: Modifier,
    label:String,
    maxLines: Int,
    keyboardType: KeyboardType
){
    CaixaDeTexto(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        maxLines = maxLines,
        keyboardType = keyboardType,
        readOnly = false
    )
}


@Composable
fun CaixaDeTexto(
    value:String,
    onValueChange :(String) -> Unit,
    modifier: Modifier,
    label:String,
    maxLines: Int,
    keyboardType: KeyboardType,
    readOnly: Boolean
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
          Text (text = label,
              fontSize = 20.sp)



        },
        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
        maxLines = maxLines,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Black,
            focusedBorderColor = Blue,
            focusedLabelColor = Black,
            backgroundColor = White,
            cursorColor = Blue
        ),
        shape = ShapeEditText.small,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType

        ),

        readOnly = readOnly

    )

}





