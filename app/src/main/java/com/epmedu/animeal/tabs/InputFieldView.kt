package com.epmedu.animeal.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.DarkerGrey
import com.epmedu.animeal.base.theme.LynxWhite
import com.epmedu.animeal.base.theme.NavigationItemColor

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

        Text(
            text = title,
            modifier = modifier.padding(bottom = 2.dp),
            style = TextStyle(
                color = NavigationItemColor,
                fontSize = 14.sp
            )
        )

        TextField(
            modifier = modifier.background(
                color = LynxWhite,
                shape = RoundedCornerShape(12.dp),
            ),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TypographyCustom,
            colors = TextFieldDefaults.textFieldColors(
                textColor = NavigationItemColor,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = DarkerGrey
                )
            },
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
