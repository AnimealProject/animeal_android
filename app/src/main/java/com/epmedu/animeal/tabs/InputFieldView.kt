package com.epmedu.animeal.tabs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputFieldView(
    modifier: Modifier,
    title: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    value: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = title, modifier = modifier.padding(bottom = 4.dp))

        TextField(
            modifier = modifier.background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp),
            ),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = placeholder) },
            singleLine = true,
            keyboardOptions = keyboardOptions
        )
    }
}
/*

@Preview(
    showBackground = true,
    name = "Preview1",
    showSystemUi = true,
    backgroundColor = 0xFF03A9F4,
    heightDp = 200
)
@Composable
fun TestPreviewInput() {
    var number by remember { mutableStateOf("") }

    InputFieldView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = "Name",
        placeHolder = "Enter your name",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            number = it
            Log.wtf("Taken Value from input", "$it")
        },
        number
    )
}*/
