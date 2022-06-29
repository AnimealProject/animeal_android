package com.epmedu.animeal.tabs

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.DarkerGrey
import com.epmedu.animeal.base.theme.LynxWhite
import com.epmedu.animeal.base.theme.NavigationItemColor

@Composable
fun TextInputField(
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
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
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

@Preview(
    showBackground = true,
    name = "Preview1",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
fun TestPreviewInput() {
    AnimealTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            var name by remember { mutableStateOf("") }

            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Name",
                placeholder = "Enter your name",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    name = it
                },
                name
            )

        }
    }
}
